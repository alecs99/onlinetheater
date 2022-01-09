package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Spectator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spectatorId;
    private String username;

    @OneToOne(mappedBy = "spectator")
    private Subscription subscription;

    @ManyToMany(mappedBy = "spectatorList")
    @JsonIgnore
    private List<Play> playList = new ArrayList<>();

    public Spectator() {
    }

    public Spectator(Integer spectatorId, String username) {
        this.spectatorId = spectatorId;
        this.username = username;
    }

    public Spectator(String username) {
        this.username = username;
    }

    public Integer getSpectatorId() {
        return spectatorId;
    }

    public void setSpectatorId(Integer spectatorId) {
        this.spectatorId = spectatorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public List<Play> getPlayList() {
        return playList;
    }

    public void setPlayList(List<Play> playList) {
        this.playList = playList;
    }
}
