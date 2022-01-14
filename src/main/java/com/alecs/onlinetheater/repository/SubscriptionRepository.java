package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Spectator;
import com.alecs.onlinetheater.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    public Optional<Subscription> findSubscriptionBySpectatorSpectatorId(int spectatorId);
}
