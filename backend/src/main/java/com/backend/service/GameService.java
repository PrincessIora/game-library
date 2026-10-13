package com.backend.service;

import com.backend.entity.Game;
import com.backend.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository repo;

    public GameService(GameRepository gameRepository) {
        this.repo = gameRepository;
    }

    public List<Game> getAllGames() {
        return repo.findAll();
    }

    public Game getGameById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Game findByName(String name) {
        return repo
                .findByNameIgnoreCase(name)
                .orElse(null);

    }

    public List<Game> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
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
        existing.setCover_url(game.getCover_url());
        existing.setDev(game.getRelease_date());
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

}