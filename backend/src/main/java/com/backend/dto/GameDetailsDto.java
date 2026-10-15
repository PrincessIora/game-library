package com.backend.dto;


import java.time.LocalDate;

public record GameDetailsDto (
    String name,
    String description,
    String headerArtworkUrl,
    LocalDate releaseDate,
    String developer,
    String publisher,
    String genres,
    String platforms
    ){
}
