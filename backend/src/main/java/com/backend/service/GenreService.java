package com.backend.service;

import com.backend.entity.Genre;
import com.backend.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository repo;

    public GenreService(GenreRepository repo) {
        this.repo = repo;
    }

    public List<Genre> getAllGenres() {
        return repo.findAll();
    }

    public Genre getGenreById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Genre findByName(String name) {
        return repo
                .findByNameIgnoreCase(name)
                .orElse(null);
    }

    public List<Genre> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public void addGenre(Genre genre) {
        if (findByName(genre.getName()) != null) {
            throw new IllegalStateException(
                    "Genre with name " + genre.getName() + " already exists"
            );
        }

        repo.save(genre);
    }

    public Boolean updateGenre(int id, Genre genre) {
        Genre existing = repo.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        existing.setName(genre.getName());
        repo.save(existing);
        return true;
    }

    public Boolean deleteGenre(int id) {
        Genre genre = repo.findById(id).orElse(null);
        if (genre != null) {
            repo.delete(genre);
            return true;
        }
        return false;
    }
}
