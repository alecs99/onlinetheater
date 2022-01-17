package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Plan;
import com.alecs.onlinetheater.service.PlanService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plan")
@Api(value = "plan", description = "In this endpoint are defined requests for the plan model " +
        "(list, insert, update or delete plans) ")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @ApiOperation(value = "View a list of available plans", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved plans list"),
            @ApiResponse(code = 404, message = "No plans were found")
    })
    @GetMapping
    public ResponseEntity<List<Plan>> listPlans() {
        return ResponseEntity.ok().body(planService.listPlans());
    }

    @ApiOperation(value = "View a plan based on a given id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the plan"),
            @ApiResponse(code = 404, message = "No plan with the given id was found")
    })
    @GetMapping("/{planId}")
    public ResponseEntity<?> listPlanById(@PathVariable int planId) {
        Optional<Plan> result = planService.findPlan(planId);

        if (result.isPresent())
            return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Add a new plan ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added the plan"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PostMapping
    public ResponseEntity<Plan> addPlan(@RequestBody @Valid Plan plan) {
        Plan savedPlan = planService.addNewPlan(plan);
        return ResponseEntity.created(URI.create("/plan/" + savedPlan.getPlanId())).body(savedPlan);
    }

    @ApiOperation(value = "Edit an existing plan ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited the plan"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PutMapping
    public ResponseEntity<Plan> editPlan(@RequestBody @Valid Plan plan) {
        return ResponseEntity.ok().body(planService.updatePlan(plan));
    }

    @ApiOperation(value = "Delete an existing plan ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the plan"),
            @ApiResponse(code = 404, message = "No plan with the given id was found")
    })
    @DeleteMapping("/{planId}")
    public ResponseEntity<String> deletePlan(@PathVariable int planId) {
        if (planService.deletePlan(planId) != -1) {
            return ResponseEntity.ok().body("Plan with the id: " + planId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }
}
