package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
}
