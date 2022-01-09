package com.alecs.onlinetheater.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playId;
    private String playName;
    private String genre;
    private String description;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room playRoom;

    @ManyToOne
    @JoinColumn(name = "playwright_id")
    private Playwright playwright;

    @ManyToMany
    @JoinTable(name = "play_spectator", joinColumns = @JoinColumn(name = "play_id"),
                inverseJoinColumns = @JoinColumn(name = "spectator_id"))
    private List<Spectator> spectatorList = new ArrayList<>();

    public Play() {
    }

    public Play(String playName, String genre, String description, Integer duration) {
        this.playName = playName;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Room getPlayRoom() {
        return playRoom;
    }

    public void setPlayRoom(Room playRoom) {
        this.playRoom = playRoom;
    }

    public List<Spectator> getSpectatorList() {
        return spectatorList;
    }

    public void setSpectatorList(List<Spectator> spectatorList) {
        this.spectatorList = spectatorList;
    }

    public Playwright getPlaywright() {
        return playwright;
    }

    public void setPlaywright(Playwright playwright) {
        this.playwright = playwright;
    }
}
