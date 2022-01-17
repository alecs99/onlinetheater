package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@ApiModel(value = "Room", description = "Room is the virtual hall where the plays are running. ")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The ID of the plan generated by database.")
    private Integer roomId;
    @NotNull(message = "Room name can not be null!")
    @NotEmpty(message = "Room name can not be empty!")
    @Length(min=3, max = 20, message = "Room name can have a length between 3 and 20 characters!")
    @ApiModelProperty(notes = "The name of the room. You can name one based of a genre, for example.")
    public String roomName;
    @NotNull(message = "Room type can not be null!")
    @NotEmpty(message = "Room type can not be empty!")
    @Length(min=3, max = 20, message = "Room type can have a length between 3 and 20 characters!")
    @ApiModelProperty(notes = "The type of the room. You should name it based on a plan name.")
    public String roomType;

    @OneToMany(mappedBy = "playRoom")
    @JsonIgnore
    private List<Play> playList = new ArrayList<>();

    public Room() {
    }

    public Room(String roomName, String roomType) {
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<Play> getPlayList() {
        return playList;
    }

    public void setPlayList(List<Play> playList) {
        this.playList = playList;
    }
}
