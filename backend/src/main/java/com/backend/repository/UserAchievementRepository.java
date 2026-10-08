package com.backend.repository;

import com.backend.entity.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  UserAchievementRepository extends JpaRepository<UserAchievement, Integer> {
    Optional<UserAchievement> findByNameIgnoreCase(String name);

    List<UserAchievement> findByCompleted(Boolean completed);

}
