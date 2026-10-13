package com.backend.service;

import com.backend.entity.User;
import com.backend.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
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

    public void addUser(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException(
                    "Username '" + user.getUsername() + "' is already in use"
            );
        }
        repo.save(user);
    }

    public Boolean updateUser(int id, User user) {
        User existing = repo.findById(id).orElse(null);
        if (existing == null) {
            return false;
        }

        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());

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