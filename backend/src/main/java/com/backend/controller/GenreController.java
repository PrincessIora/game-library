package com.backend.controller;

import com.backend.entity.Genre;
import com.backend.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@CrossOrigin(origins = "*")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(
                genreService.getAllGenres()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable int id) {
        Genre genre = genreService.getGenreById(id);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        genreService.addGenre(genre);

        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(
            @PathVariable int id, @RequestBody Genre genre) {
        boolean success = genreService.updateGenre(id, genre);

        if (!success) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                genreService.getGenreById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(
            @PathVariable int id) {
        boolean deleted = genreService.deleteGenre(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }


}

