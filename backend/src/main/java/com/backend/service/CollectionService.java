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
        return repo.findByOwnerShipStatus(ownershipStatus);
    }

    public void addCollection(Collection collection) {
        if (repo.findByUserId(collection.getUser_id()) != null) {
            throw new IllegalArgumentException("Collection already exists");
        }
        repo.save(collection);
    }

    public boolean updateCollection(Collection collection) {
        Collection existing = repo.findById(collection.getId()).orElse(null);

        if (existing == null) {
            return false;
        }

        if (existing.getUser_id() != collection.getUser_id()) {
            return false;
        }
        existing.setGame_ids(collection.getGame_ids());
        existing.setOwnership_status(collection.getOwnership_status());
        existing.setPlay_status(collection.getPlay_status());
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
