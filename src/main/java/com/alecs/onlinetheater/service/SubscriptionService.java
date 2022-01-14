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

    public Subscription addOrUpdateSubscription(Spectator spectator, int planId, LocalDateTime startDate) {

        Subscription spectatorSubscription = subscriptionRepository
                .findSubscriptionBySpectatorSpectatorId(spectator.getSpectatorId())
                .orElse(new Subscription(startDate, startDate.plusDays(30)));

        Plan requestedPlan = planService.findPlan(planId)
                                        .orElseThrow(() -> new DataAccessException("Requested plan not found!") {});

        if(checkValidSubscription(spectatorSubscription)) {
            spectatorSubscription.setSubscriptionStartDate(LocalDateTime.now());
            spectatorSubscription.setSubscriptionEndDate(LocalDateTime.now().plusDays(30));
        }

        spectatorSubscription.setSpectator(spectator);
        spectatorSubscription.setSubscriptionPlan(requestedPlan);

        return subscriptionRepository.save(spectatorSubscription);
    }

    public Boolean checkValidSubscription(Subscription subscription) {
        return LocalDateTime.now().isAfter(subscription.getSubscriptionEndDate());
    }
}
