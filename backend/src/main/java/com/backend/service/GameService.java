package com.backend.service;

import com.backend.dto.GameDetailsDto;
import com.backend.entity.Game;
import com.backend.repository.GameRepository;
import org.springframework.stereotype.Service;
import com.backend.entity.Genre;
import com.backend.entity.Platform;
import com.backend.repository.GenreRepository;
import com.backend.repository.PlatformRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Service
public class GameService {
    private final GameRepository repo;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;


    public GameService(GameRepository gameRepository, GenreRepository genreRepository, PlatformRepository platformRepository) {
        this.repo = gameRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
    }

    public List<Game> getAllGames() {
        return repo.findAll();
    }

    public Game getGameById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Game findByName(String name) {
        return repo
                .findByTitleIgnoreCase(name)
                .orElse(null);

    }

    public List<Game> searchByName(String name) {
        return repo.findByTitleContainingIgnoreCase(name);
    }

    public List<Game> searchByDev(String dev) {
        return repo.findByDevContainingIgnoreCase(dev);
    }

    public List<Game> searchByPublisher(String pub) {
        return repo.findByPublisherContainingIgnoreCase(pub);
    }


    public void addGame(Game game) {
        if (findByName(game.getTitle()) != null) {
            throw new IllegalArgumentException(
                    "Game with the name '" + game.getTitle() + "' already exists."
            );
        }
        repo.save(game);
    }

    public Boolean updateGame(int id, Game game) {
        Game existing = repo.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }
        existing.setTitle(game.getTitle());
        existing.setDescription(game.getDescription());
        existing.setCoverUrl(game.getCoverUrl());
        existing.setDev(game.getReleaseDate());
        existing.setDev(game.getDev());
        existing.setPublisher(game.getPublisher());
        repo.save(existing);
        return true;
    }

    public Boolean deleteGame(int id) {
        Game game = repo.findById(id).orElse(null);
        if (game != null) {
            repo.delete(game);
            return true;
        }
        return false;
    }



    @Transactional
    public Game importSteamGame(long appId, GameDetailsDto details) {
        if (details == null) {
            throw new IllegalArgumentException(
                    "Steam game details are required"
            );
        }

        // Prevent importing the same Steam game twice.
        Game existing = repo.findByExternalGameId(appId)
                .orElse(null);

        if (existing != null) {
            return existing;
        }

        Game game = new Game();

        game.setExternalGameId(appId);
        game.setTitle(details.name());
        game.setDescription(details.description());
        game.setCoverUrl(details.headerArtworkUrl());

        game.setReleaseDate(
                details.releaseDate() == null
                        ? null
                        : details.releaseDate().toString()
        );

        game.setDev(details.developer());
        game.setPublisher(details.publisher());

        // Resolve names to actual Genre and Platform entities.
        game.setGenres(resolveGenres(details.genres()));
        game.setPlatforms(resolvePlatforms(details.platforms()));

        return repo.save(game);
    }


    private List<Genre> resolveGenres(String genreNames) {
        if (genreNames == null || genreNames.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(genreNames.split(","))
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .distinct()
                .map(name -> genreRepository
                        .findByNameIgnoreCase(name)
                        .orElseGet(() ->
                                genreRepository.save(new Genre(name))
                        ))
                .toList();
    }

    private List<Platform> resolvePlatforms(String platformNames) {
        if (platformNames == null || platformNames.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(platformNames.split(","))
                .map(String::trim)
                .filter(name -> !name.isBlank())
                .distinct()
                .map(name -> platformRepository
                        .findByNameIgnoreCase(name)
                        .orElseGet(() ->
                                platformRepository.save(
                                        new Platform(name)
                                )
                        ))
                .toList();
    }


}