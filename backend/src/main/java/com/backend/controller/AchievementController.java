package com.backend.controller;


import com.backend.entity.Achievement;
import com.backend.service.AchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
@CrossOrigin(origins = "*")
public class AchievementController {
    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public ResponseEntity<List<Achievement>> getAllAchievements() {
        return ResponseEntity.ok(
                achievementService.getAllAchievements()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achievement> getAchievement(@PathVariable int id) {
        Achievement achievement = achievementService.getAchievementById(id);

        if (achievement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(achievement);
    }

    @PostMapping
    public ResponseEntity<Achievement> addAchievement(@RequestBody Achievement achievement) {
        achievementService.addAchievement(achievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(achievement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achievement> updateAchievement(
            @PathVariable int id,
            @RequestBody Achievement achievement
    ) {
        boolean success =
                achievementService.updateAchievement(id, achievement);

        if (!success) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(achievementService.getAchievementById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable int id) {
        boolean deleted = achievementService.deleteAchievement(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
