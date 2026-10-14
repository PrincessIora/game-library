package com.backend.service;

import com.backend.entity.UserAchievement;
import com.backend.repository.UserAchievementRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAchievementService {
    private final UserAchievementRepository repo;

    public UserAchievementService(UserAchievementRepository repo) {
        this.repo = repo;
    }

    public List<UserAchievement> getAllUserAchievements() {
        return repo.findAll();
    }

    public UserAchievement getUserAchievementById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<UserAchievement> findUserAchievementByAchievementId(int id) {
        return repo.findByAchievementId(id);
    }

    public List<UserAchievement> findByCompleted(Boolean completed) {
        return repo.findByCompleted(completed);
    }

    public void addUserAchievement(UserAchievement userAchievement) {
        if (repo.findById(userAchievement.getId()).isPresent()) {
            throw new IllegalStateException(
                    "UserAchievement already exists"
            );
        }
        repo.save(userAchievement);

    }

    public Boolean updateUserAchievement(int id, UserAchievement userAchievement) {
        UserAchievement existing = repo.findById(id).orElse(null);
        if (existing == null) {
            return false;
        }
        existing.setAchievement_id(userAchievement.getAchievementId());
        existing.setCompleted(userAchievement.isCompleted());
        existing.setCompletedAt(userAchievement.getCompletedAt());
        repo.save(existing);
        return true;
    }

    public Boolean deleteUserAchievement(int id) {
        UserAchievement achievement = repo.findById(id).orElse(null);
        if (achievement != null) {
            repo.delete(achievement);
            return true;
        }
        return false;
    }

}
