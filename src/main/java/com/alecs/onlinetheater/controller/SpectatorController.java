package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.model.Spectator;
import com.alecs.onlinetheater.service.SpectatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spectator")
//@Api(value = "spectator", description = "In this endpoint are defined the methods necessary for the enduser to " +
//        "interact with the aplication, for example: see information about himself, add a subscription or watch a play.")
public class SpectatorController {
    private final SpectatorService spectatorService;

    public SpectatorController(SpectatorService spectatorService) {
        this.spectatorService = spectatorService;
    }

//    @ApiOperation(value = "View the information about a spectator ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved the spectator information"),
//            @ApiResponse(code = 404, message = "No spectator was found with the given id")
//    })
    @GetMapping("/{spectatorId}")
    public ResponseEntity<?> listSpectatorById(@PathVariable int spectatorId) {
        Optional<Spectator> result = spectatorService.getSpectatorDetails(spectatorId);

        if (result.isPresent())
            return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

//    @ApiOperation(value = "View the spectator's watchlist ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved the spectator watchlist"),
//            @ApiResponse(code = 404, message = "No watchlist found for the spectator")
//    })
    @GetMapping("/play")
    public ResponseEntity<List<Play>> listWatchedPlays(@RequestBody @Valid Spectator spectator) {
        return ResponseEntity.ok().body(spectatorService.getSpectatorPlays(spectator));
    }

//    @ApiOperation(value = "Add a new spectator ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully added the spectator"),
//            @ApiResponse(code = 404, message = "Resource not found")
//    })
    @PostMapping
    public ResponseEntity<Spectator> addSpectator (@RequestBody @Valid Spectator spectator) {
        Spectator savedSpectator = spectatorService.addNewSpectator(spectator);
        return ResponseEntity.created(URI.create(savedSpectator.getSpectatorId().toString())).body(savedSpectator);
    }

//    @ApiOperation(value = "Watch a play ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully watched the play"),
//            @ApiResponse(code = 404, message = "Resource not found")
//    })
    @PostMapping("/{spectatorId}/watch/{playId}")
    public ResponseEntity<Spectator> watchPlay (@PathVariable int spectatorId,
                                           @PathVariable int playId) {
        return ResponseEntity.ok().body(spectatorService.watchPlay(spectatorId, playId));
    }

//    @ApiOperation(value = "Edit spectator information ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully edited the spectator"),
//            @ApiResponse(code = 404, message = "Resource not found")
//    })
    @PutMapping
    public ResponseEntity<Spectator> editSpectator (@RequestBody @Valid Spectator spectator) {
        return ResponseEntity.ok().body(spectatorService.updateSpectator(spectator));
    }

//    @ApiOperation(value = "Buy a subscription ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully bought a subscription"),
//            @ApiResponse(code = 404, message = "Resource not found")
//    })
    @PutMapping("/subscription/{planId}")
    public ResponseEntity<Spectator> addOrUpdateSubscription(@RequestBody @Valid Spectator spectator,
                                                     @PathVariable int planId) {
        return ResponseEntity.ok().body(spectatorService.addOrUpdateSubscription(spectator, planId));
    }

//    @ApiOperation(value = "Delete an existing spectator ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully deleted the spectator"),
//            @ApiResponse(code = 404, message = "No spectator with the given id was found")
//    })
    @DeleteMapping("/{spectatorId}")
    public ResponseEntity<String> deleteSpectator (@PathVariable int spectatorId) {
        if (spectatorService.deleteSpectator(spectatorId) != -1) {
            return ResponseEntity.ok().body("Plan with the id: " + spectatorId + " was successfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }
}
