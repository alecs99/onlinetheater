package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playId;
    @NotNull
    @NotEmpty
    @Length(min=3, max = 15, message = "Play can have a length between 3 and 15 characters!")
    private String playName;
    @NotNull
    @NotEmpty
    @Length(min=3, max = 10, message = "Genre can have a length between 3 and 10 characters!")
    private String genre;
    @NotNull
    @NotEmpty
    @Length(min=10, max = 1000, message = "Description can have a length between 10 and 1000 characters!")
    private String description;
    @Min(value = 10, message = "Play should be at least 10 minutes long")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room playRoom;

    @ManyToOne
    @JoinColumn(name = "playwright_id")
    private Playwright playwright;

    @ManyToMany(mappedBy = "playList")
    @JsonIgnore
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
