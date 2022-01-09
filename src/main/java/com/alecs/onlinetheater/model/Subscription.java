package com.alecs.onlinetheater.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subscriptionId;
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;

    @OneToOne
    @JoinColumn(name="spectator_id")
    @JsonIgnore
    private Spectator spectator;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan subscriptionPlan;

    public Subscription() {
    }

    public Subscription(LocalDateTime subscriptionStartDate, LocalDateTime subscriptionEndDate) {
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public LocalDateTime getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDateTime getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public Plan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(Plan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }
}
