package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Playwright {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Date birthday;

    @OneToMany(mappedBy = "playwright")
    @JsonIgnore
    private List<Play> writtenPlays;

    public Playwright() {
    }

    public Playwright(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Play> getWrittenPlays() {
        return writtenPlays;
    }

    public void setWrittenPlays(List<Play> writtenPlays) {
        this.writtenPlays = writtenPlays;
    }
}
