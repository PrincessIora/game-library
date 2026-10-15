package com.backend.controller;

import com.backend.dto.GameDetailsDto;
import com.backend.dto.SteamGameDto;
import com.backend.service.SteamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steam")
public class SteamController {

    private final SteamService steamService;

    public SteamController(SteamService steamService) {
        this.steamService = steamService;
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
}
