package com.backend.repository;

import com.backend.entity.Game;
import com.backend.entity.enums.OwnershipStatus;
import com.backend.entity.enums.PlayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByNameIgnoreCase(String title);

    List<Game> findByNameContainingIgnoreCase(String title);
    List<Game> findByDevContainingIgnoreCase(String dev);
    List<Game> findByPublisherContainingIgnoreCase(String pub);

}
