package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Plan;
import com.alecs.onlinetheater.model.Spectator;
import com.alecs.onlinetheater.model.Subscription;
import com.alecs.onlinetheater.repository.SubscriptionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanService planService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanService planService) {
        this.subscriptionRepository = subscriptionRepository;
        this.planService = planService;
    }

    public Subscription addNewSubscription(Spectator spectator, int planId, LocalDateTime startDate) {
        Optional<Plan> requestedPlan = planService.findPlan(planId);

        if(!requestedPlan.isPresent())
            throw new DataAccessException("Requested plan not found!") {};

        Subscription spectatorSubscription = new Subscription(startDate, startDate.plusDays(30));
        spectatorSubscription.setSpectator(spectator);
        spectatorSubscription.setSubscriptionPlan(requestedPlan.get());

        return subscriptionRepository.save(spectatorSubscription);
    }
}
