package com.backend.dto;

public record SteamGameDto(
        long steamAppId,
        String name,
        String imageUrl
) {
}