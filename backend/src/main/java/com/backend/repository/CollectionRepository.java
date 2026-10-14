package com.backend.repository;

import com.backend.entity.Collection;
import com.backend.entity.enums.OwnershipStatus;
import com.backend.entity.enums.PlayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  CollectionRepository extends JpaRepository<Collection, Integer> {
    List<Collection> findByUserId(int id);
    List<Collection> findByPlayStatus(PlayStatus playStatus);
    List<Collection> findByOwnershipStatus(OwnershipStatus ownershipStatus);

}
