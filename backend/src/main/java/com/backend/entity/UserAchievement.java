package com.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "achievement_id")
    private int achievement_id;
    private boolean completed;
    private LocalDateTime completed_at;

    public UserAchievement(int achievement_id, boolean completed) {
        this.achievement_id = achievement_id;
        this.completed = completed;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(LocalDateTime completed_at) {
        this.completed_at = completed_at;
    }
}
