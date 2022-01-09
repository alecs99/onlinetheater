package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
