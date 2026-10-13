package com.backend.repository;

import com.backend.entity.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Integer> {

    List<UserAchievement> findByAchievementId(int id);
    List<UserAchievement> findByCompleted(Boolean completed);

}
