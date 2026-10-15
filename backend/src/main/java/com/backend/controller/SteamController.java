package com.backend.controller;

import com.backend.dto.GameDetailsDto;
import com.backend.dto.SteamGameDto;
import com.backend.entity.Game;
import com.backend.service.GameService;
import com.backend.service.SteamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steam")
public class SteamController {

    private final SteamService steamService;
    private final GameService gameService;

    public SteamController(
            SteamService steamService, GameService gameService
    ) {
        this.steamService = steamService;
        this.gameService = gameService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchGames(
            @RequestParam String term) {

        if (term == null || term.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Search term is required");
        }

        List<SteamGameDto> games =
                steamService.searchGames(term);

        return ResponseEntity.ok(games);
    }

    @GetMapping("/appdetails")
    public ResponseEntity<?> getAppDetails(@RequestParam long appId) {

        if (appId <= 0) {
            return ResponseEntity.badRequest()
                    .body("A valid Steam App ID is required");
        }
        GameDetailsDto gameInfo =
                steamService.getGameDetails(appId);
        if(gameInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameInfo);

    }


    @PostMapping("/import/{appId}")
    public ResponseEntity<?> importGame(
            @PathVariable long appId
    ) {
        if (appId <= 0) {
            return ResponseEntity.badRequest()
                    .body("A valid Steam App ID is required");
        }

        // Fetch complete game details from Steam.
        GameDetailsDto details =
                steamService.getGameDetails(appId);

        if (details == null) {
            return ResponseEntity.notFound().build();
        }

        // Convert and persist the game.
        Game savedGame =
                gameService.importSteamGame(appId, details);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedGame);
    }

}
