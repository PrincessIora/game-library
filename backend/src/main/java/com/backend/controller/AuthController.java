package com.backend.controller;

import com.backend.entity.User;
import com.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.backend.dto.UserResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getUsername() == null ||
                user.getUsername().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Username is required");
        }
        if (user.getPassword() == null ||
                user.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Password is required");
        }
        if (user.getPassword().length() < 6) {
            return ResponseEntity.badRequest()
                    .body("Password must be at least 6 characters");
        }

        try {

            User savedUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponse(savedUser.getId(), savedUser.getUsername()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser( Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication is required");
        }
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername()));
    }

    @PostMapping("/logout")
public ResponseEntity<?> logout(){
        return ResponseEntity.ok("Logged out successfully");
    }

}
