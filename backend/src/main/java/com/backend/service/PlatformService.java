package com.backend.service;

import com.backend.entity.Platform;
import com.backend.repository.PlatformRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {
    private final PlatformRepository repo;

    public PlatformService(PlatformRepository repo) {
        this.repo = repo;
    }

    public List<Platform> findAllPlatforms() {
        return repo.findAll();
    }

    public Platform getPlatformById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Platform findByName(String name) {
        return repo
                .findByNameIgnoreCase(name)
                .orElse(null);
    }

    public List<Platform> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public void addPlatform(Platform platform) {
        if (findByName(platform.getName()) != null) {
            throw new IllegalStateException(
                    "Platform with name " + platform.getName() + " already exists"
            );
        }

        repo.save(platform);
    }

    public Boolean updatePlatform(int id, Platform platform) {
        Platform existing = repo.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        existing.setName(platform.getName());
        repo.save(existing);
        return true;
    }

    public Boolean deletePlatform(int id) {
        Platform platform = repo.findById(id).orElse(null);
        if (platform != null) {
            repo.delete(platform);
            return true;
        }
        return false;
    }
}


