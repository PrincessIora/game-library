package com.backend.repository;

import com.backend.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  AchievementRepository extends JpaRepository<Achievement, Integer> {


    Optional<Achievement> findByNameIgnoreCase(String name);

    List<Achievement> findByNameContainingIgnoreCase(String name);


}
