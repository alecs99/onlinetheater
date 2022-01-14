package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Spectator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spectatorId;
    @NotNull(message = "Username can not be null!")
    @NotEmpty(message = "Username can not be empty!")
    @Length(min=3, max = 10, message = "Username can have a length between 3 and 10 characters!")
    private String username;

    @OneToOne(mappedBy = "spectator")
    private Subscription subscription;

    @ManyToMany
    @JoinTable(name = "play_spectator", joinColumns = @JoinColumn(name = "spectator_id"),
            inverseJoinColumns = @JoinColumn(name = "play_id"))
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
