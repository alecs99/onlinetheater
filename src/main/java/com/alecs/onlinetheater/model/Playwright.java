package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
public class Playwright {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Playwright name can not be null!")
    @NotEmpty(message = "Playwright name can not be empty!")
    @Length(min=3, max = 30, message = "Playwright name can have a length between 3 and 20 characters!")
    private String name;
//    @Pattern(regexp = "([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}",
//            message = "Birthday should have the format dd-mm-yyyy")
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

    public Date getBirthday() { return birthday; }

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
