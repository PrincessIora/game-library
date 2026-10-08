package com.backend.repository;

import com.backend.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  PlatformRepository extends JpaRepository<Platform, Integer> {
    Optional<Platform> findByNameIgnoreCase(String name);

    List<Platform> findByNameContainingIgnoreCase(String title);

}
