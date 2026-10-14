package com.backend.repository;

import com.backend.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByTitleIgnoreCase(String title);

    List<Game> findByTitleContainingIgnoreCase(String title);
    List<Game> findByDevContainingIgnoreCase(String dev);
    List<Game> findByPublisherContainingIgnoreCase(String pub);

}
