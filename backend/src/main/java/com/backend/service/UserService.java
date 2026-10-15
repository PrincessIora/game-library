package com.backend.service;

import com.backend.entity.User;
import com.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.encoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(int id) {
        return repo.findById(id).get();
    }

    public User findByUsername(String username) {
        return repo
                .findByUsernameIgnoreCase(username)
                .orElse(null);
    }

    public List<User> searchByUserName(String username) {
        return repo.findByUsernameContainingIgnoreCase(username);
    }

    public User addUser(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException(
                    "Username '" + user.getUsername() + "' is already in use"
            );
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public Boolean updateUser(int id, User user) {
        User existing = repo.findById(id).orElse(null);
        if (existing == null) {
            return false;
        }

        existing.setUsername(user.getUsername());
        if(user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(encoder.encode(user.getPassword()));
        }
        repo.save(existing);
        return true;

    }

    public Boolean deleteUser(int id) {
        User user = repo.findById(id).orElse(null);
        if (user != null) {
            repo.delete(user);
            return true;
        }
        return false;
    }
}