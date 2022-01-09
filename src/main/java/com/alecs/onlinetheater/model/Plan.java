package com.alecs.onlinetheater.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;
    @NotNull(message = "Plan name can not be null!")
    @NotEmpty(message = "Plan name can not be empty!")
    private String planName;
    @NotEmpty(message = "Plan description can not be empty!")
    private String planDescription;
    @Min(value = 1, message = "Price should be at least 1!")
    private Integer price;

    public Plan() {
    }

    public Plan(String planName, String planDescription, Integer price) {
        this.planName = planName;
        this.planDescription = planDescription;
        this.price = price;
    }

    public Plan(Integer planId, String planName, String planDescription, Integer price) {
        this.planId = planId;
        this.planName = planName;
        this.planDescription = planDescription;
        this.price = price;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
