package com.backend.entity;

import com.backend.entity.enums.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private List<Integer> gameId;

    private OwnershipStatus ownershipStatus = OwnershipStatus.WANTED;
    private PlayStatus playStatus =  PlayStatus.NOT_YET_PLAYED;

    public Collection() {

    }

    public Collection(int userId, int gameId) {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getGameIds() {
        return gameId;
    }

    public void setGameIds(List<Integer> gameId) {
        this.gameId = gameId;
    }

    public OwnershipStatus getOwnershipStatus() {
        return ownershipStatus;
    }

    public void setOwnership_status(OwnershipStatus ownershipStatus) {
        this.ownershipStatus = ownershipStatus;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }
}