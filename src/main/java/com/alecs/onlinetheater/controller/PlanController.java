package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Plan;
import com.alecs.onlinetheater.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<Plan>> listPlans() {
        return ResponseEntity.ok().body(planService.listPlans());
    }

    @GetMapping("/{planId}")
    public ResponseEntity<?> listPlanById(@PathVariable int planId) {
        Optional<Plan> result = planService.findPlan(planId);

        if (result.isPresent())
             return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Plan> addPlan (@RequestBody @Valid Plan plan) {
        Plan savedPlan = planService.addNewPlan(plan);
        return ResponseEntity.created(URI.create(savedPlan.getPlanId().toString())).body(savedPlan);
    }

    @PutMapping
    public ResponseEntity<Plan> editPlan (@RequestBody @Valid Plan plan) {
        return ResponseEntity.ok().body(planService.updatePlan(plan));
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<String> deletePlan (@PathVariable int planId) {
        if (planService.deletePlan(planId) != -1) {
            return ResponseEntity.ok().body("Plan with the id: " + planId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }
}
