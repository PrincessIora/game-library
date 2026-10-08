package com.backend.repository;

import com.backend.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  CollectionRepository extends JpaRepository<Collection, Integer> {
    List<Collection> findByUserId(int id);
    List<Collection> findByGameId(int id);

}
