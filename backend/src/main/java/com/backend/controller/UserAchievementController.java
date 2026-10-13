package com.backend.controller;

import com.backend.entity.UserAchievement;
import com.backend.service.UserAchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userAchievements")
@CrossOrigin(origins = "*")
public class UserAchievementController {

    private final UserAchievementService userAchievementService;

    public UserAchievementController(UserAchievementService userAchievementService) {
        this.userAchievementService = userAchievementService;
    }

    @GetMapping
    public ResponseEntity<List<UserAchievement>> getAllUserAchievements() {
        return ResponseEntity.ok(
                userAchievementService.getAllUserAchievements()
        );
    }

    @GetMapping("/{id}")


    public ResponseEntity<UserAchievement> getUserAchievement(
            @PathVariable int id) {
        UserAchievement userAchievement = userAchievementService.getUserAchievementById(id);

        if (userAchievement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAchievement);
    }

    @PostMapping
    public ResponseEntity<UserAchievement> createUserAchievement(
            @RequestBody UserAchievement userAchievement
    ) {
        userAchievementService.addUserAchievement(userAchievement);

        return ResponseEntity.status(HttpStatus.CREATED).body(userAchievement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAchievement> updateUserAchievement(
            @PathVariable int id, @RequestBody UserAchievement userAchievement
    ) {
        boolean success = userAchievementService.updateUserAchievement(id, userAchievement);

        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAchievementService.getUserAchievementById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserAchievement> deleteUserAchievement(
            @PathVariable int id
    ) {
        boolean deleted = userAchievementService.deleteUserAchievement(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAchievementService.getUserAchievementById(id));
    }


}
