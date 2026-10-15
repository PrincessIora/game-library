package com.backend.service;


import com.backend.dto.GameDetailsDto;
import com.backend.dto.SteamGameDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SteamService {
    private final RestClient restClient;

    public SteamService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .build();
    }

    public List<SteamGameDto> searchGames(String term) {
        if (term == null || term.isBlank()) {
            throw new IllegalArgumentException(
                    "Search term is required"
            );
        }

        String url = UriComponentsBuilder
                .fromUriString(
                        "https://store.steampowered.com/api/storesearch/"
                )
                .queryParam("term", term.trim())
                .queryParam("l", "english")
                .queryParam("cc", "US")
                .build()
                .encode()
                .toUriString();

        JsonNode response = restClient.get()
                .uri(url)
                .retrieve()
                .body(JsonNode.class);

        List<SteamGameDto> games = new ArrayList<>();

        if (response == null ||
                !response.has("items") ||
                !response.get("items").isArray()) {
            return games;
        }

        for (JsonNode item : response.get("items")) {
            long appId = item.path("id").asLong(0);
            String name = item.path("name").asText("");
            String imageUrl = item.path("tiny_image").asText("");

            if (appId > 0 && !name.isBlank()) {
                games.add(new SteamGameDto(
                        appId,
                        name,
                        imageUrl.isBlank() ? null : imageUrl
                ));
            }
        }

        return games;
    }

    public GameDetailsDto getGameDetails(long appId) {
        if (appId <= 0) {
            throw new IllegalArgumentException(
                    "A valid Steam App ID is required");
        }

        String url = UriComponentsBuilder
                .fromUriString(
                        "https://store.steampowered.com/api/appdetails")
                .queryParam("appids", appId)
                .queryParam("l", "english")
                .queryParam("cc", "US")
                .build()
                .encode()
                .toUriString();

        JsonNode response = restClient.get()
                .uri(url)
                .retrieve()
                .body(JsonNode.class);

        if (response == null) {
            return null;
        }

        // Steam wraps the result under the App ID key.
        JsonNode appResult = response.path(String.valueOf(appId));

        if (!appResult.path("success").asBoolean(false)) {
            return null;
        }

        JsonNode data = appResult.path("data");

        String name = data.path("name").asText("");
        String description =
                data.path("short_description").asText("");
        String headerArtworkUrl =
                data.path("header_image").asText("");

        String developer = joinArray(data.path("developers"));
        String publisher = joinArray(data.path("publishers"));
        String genres = joinGenreNames(data.path("genres"));
        String platforms = joinPlatforms(data.path("platforms"));

        LocalDate releaseDate = parseReleaseDate(
                data.path("release_date").path("date").asText("")
        );

        return new GameDetailsDto(
                name,
                description,
                headerArtworkUrl,
                releaseDate,
                developer,
                publisher,
                genres,
                platforms
        );
    }

    private String joinArray(JsonNode array) {
        if (!array.isArray()) {
            return "";
        }

        List<String> values = new ArrayList<>();

        for (JsonNode item : array) {
            String value = item.asText("");
            if (!value.isBlank()) {
                values.add(value);
            }
        }

        return String.join(", ", values);
    }

    private String joinGenreNames(JsonNode genresArray) {
        if (!genresArray.isArray()) {
            return "";
        }

        List<String> values = new ArrayList<>();

        for (JsonNode genre : genresArray) {
            String value = genre.path("description").asText("");
            if (!value.isBlank()) {
                values.add(value);
            }
        }

        return String.join(", ", values);
    }

    private String joinPlatforms(JsonNode platformsNode) {
        List<String> values = new ArrayList<>();

        if (platformsNode.path("windows").asBoolean(false)) {
            values.add("Windows");
        }
        if (platformsNode.path("mac").asBoolean(false)) {
            values.add("macOS");
        }
        if (platformsNode.path("linux").asBoolean(false)) {
            values.add("Linux");
        }

        return String.join(", ", values);
    }


    private LocalDate parseReleaseDate(String rawDate) {
        if (rawDate == null || rawDate.isBlank()) {
            return null;
        }

        String date = rawDate.trim();

        // Steam sometimes uses placeholders rather than actual dates.
        if (date.equalsIgnoreCase("Coming Soon")
                || date.equalsIgnoreCase("To be announced")
                || date.equalsIgnoreCase("TBA")
                || date.equalsIgnoreCase(" TBD")) {
            return null;
        }

        // Try Steam's common date format first.
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern(
                        "d MMM, yyyy",
                        java.util.Locale.ENGLISH
                ),
                DateTimeFormatter.ofPattern(
                        "MMM d, yyyy",
                        java.util.Locale.ENGLISH
                ),
                DateTimeFormatter.ISO_LOCAL_DATE
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next supported format.
            }
        }

        return null;
    }


}
