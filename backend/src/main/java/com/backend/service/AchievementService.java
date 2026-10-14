package com.backend.service;

import com.backend.entity.Achievement;
import com.backend.repository.AchievementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {
    private final AchievementRepository repo;

    public AchievementService(AchievementRepository repo) {
        this.repo = repo;
    }

    public List<Achievement> getAllAchievements() {
        return repo.findAll();
    }

    public Achievement getAchievementById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Achievement findByName(String name) {
        return repo
                .findByNameIgnoreCase(name)
                .orElse(null);
    }

    public List<Achievement> getAchievementsByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public void addAchievement(Achievement achievement) {
        if (findByName(achievement.getName()) != null) {
            throw new IllegalArgumentException("Achievement with name " + achievement.getName() + " already exists");
        }
        repo.save(achievement);
    }

    public boolean updateAchievement(int id, Achievement achievement) {
        Achievement existing = repo.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        existing.setName(achievement.getName());
        existing.setDescription(achievement.getDescription());
        existing.setIconUrl(achievement.getIconUrl());
        repo.save(existing);
        return true;
    }

    public boolean deleteAchievement(int id) {
        Achievement achievement = repo.findById(id).orElse(null);
        if (achievement != null) {
            repo.delete(achievement);
            return true;
        }
        return false;
    }

}
