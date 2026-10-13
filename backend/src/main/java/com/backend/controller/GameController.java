package com.backend.controller;

import com.backend.entity.Game;
import com.backend.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(
            @PathVariable int id
    ) {
        Game game = gameService.getGameById(id);

        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(
            @RequestBody Game game
    ) {
        gameService.addGame(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(
            @PathVariable int id,
            @RequestBody Game game
    ) {
        boolean success = gameService.updateGame(id, game);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                gameService.getGameById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(
            @PathVariable int id
    ) {
        boolean deleted = gameService.deleteGame(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

