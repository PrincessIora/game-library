package com.backend.service;

import com.backend.entity.Collection;
import com.backend.entity.enums.OwnershipStatus;
import com.backend.entity.enums.PlayStatus;
import com.backend.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    private final CollectionRepository repo;

    public CollectionService(CollectionRepository repo) {
        this.repo = repo;
    }

    public List<Collection> getAllCollections() {
        return repo.findAll();
    }

    public Collection getCollectionById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Collection> searchByPlayStatus(PlayStatus playStatus) {
        return repo.findByPlayStatus(playStatus);
    }

    public List<Collection> searchByOwnerShipStatus(OwnershipStatus ownershipStatus) {
        return repo.findByOwnershipStatus(ownershipStatus);
    }

    public void addCollection(Collection collection) {
        if (repo.findByUserId(collection.getUserId()) != null) {
            throw new IllegalArgumentException("Collection already exists");
        }
        repo.save(collection);
    }

    public boolean updateCollection(int id, Collection collection) {
        Collection existing = repo.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        if (existing.getUserId() != collection.getUserId()) {
            return false;
        }
        existing.setGameIds(collection.getGameIds());
        existing.setOwnership_status(collection.getOwnershipStatus());
        existing.setPlayStatus(collection.getPlayStatus());
        repo.save(existing);
        return true;
    }

    public boolean deleteCollection(int id) {
        Collection collection = repo.findById(id).orElse(null);
        if (collection != null) {
            repo.delete(collection);
            return true;
        }
        return false;
    }
}
