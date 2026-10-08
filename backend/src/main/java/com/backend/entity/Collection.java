package com.backend.entity;

import com.backend.entity.enums.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private int user_id;

    @OneToMany
    @JoinColumn(name = "game_id")
    private int game_id;

    private OwnershipStatus ownership_status = OwnershipStatus.WANTED;
    private PlayStatus play_status =  PlayStatus.NOT_YET_PLAYED;

    public Collection() {

    }

    public Collection(int user_id, int game_id) {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public OwnershipStatus getOwnership_status() {
        return ownership_status;
    }

    public void setOwnership_status(OwnershipStatus ownership_status) {
        this.ownership_status = ownership_status;
    }

    public PlayStatus getPlay_status() {
        return play_status;
    }

    public void setPlay_status(PlayStatus play_status) {
        this.play_status = play_status;
    }
}