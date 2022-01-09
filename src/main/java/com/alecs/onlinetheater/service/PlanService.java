package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Plan;
import com.alecs.onlinetheater.repository.PlanRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listPlans() {
        return planRepository.findAll();
    }

    public Optional<Plan> findPlan(int planId) {
        return planRepository.findById(planId);
    }

    public Plan addNewPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Plan updatePlan(Plan plan) {
        if(planRepository.existsById(plan.getPlanId())) {
            return planRepository.save(plan);
        }

        throw new DataAccessException("Plan not found") {};
    }

    public int deletePlan(int planId) {
        if (planRepository.existsById(planId)) {
            planRepository.deleteById(planId);
            return planId;
        }

        throw new DataAccessException("Plan not found") {};
    }

}
