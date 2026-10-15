package com.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

@Table(name = "user_achievements")
public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int achievementId;
    private boolean completed;
    private LocalDateTime completedAt;

    public UserAchievement(int achievementId, boolean completed) {
        this.achievementId = achievementId;
        this.completed = completed;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievement_id(int achievementId) {
        this.achievementId = achievementId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
