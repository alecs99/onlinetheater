package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.model.Spectator;
import com.alecs.onlinetheater.service.SpectatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spectator")
public class SpectatorController {
    private final SpectatorService spectatorService;

    public SpectatorController(SpectatorService spectatorService) {
        this.spectatorService = spectatorService;
    }

    @GetMapping("/{spectatorId}")
    public ResponseEntity<?> listSpectatorById(@PathVariable int spectatorId) {
        Optional<Spectator> result = spectatorService.getSpectatorDetails(spectatorId);

        if (result.isPresent())
            return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/play")
    public ResponseEntity<List<Play>> listWatchedPlays(@RequestBody @Valid Spectator spectator) {
        return ResponseEntity.ok().body(spectatorService.getSpectatorPlays(spectator));
    }

    @PostMapping
    public ResponseEntity<Spectator> addSpectator (@RequestBody @Valid Spectator spectator) {
        Spectator savedSpectator = spectatorService.addNewSpectator(spectator);
        return ResponseEntity.created(URI.create(savedSpectator.getSpectatorId().toString())).body(savedSpectator);
    }

    @PostMapping("/{spectatorId}/watch/{playId}")
    public ResponseEntity<Spectator> watchPlay (@PathVariable int spectatorId,
                                           @PathVariable int playId) {
        return ResponseEntity.ok().body(spectatorService.watchPlay(spectatorId, playId));
    }

    @PutMapping
    public ResponseEntity<Spectator> editSpectator (@RequestBody @Valid Spectator spectator) {
        return ResponseEntity.ok().body(spectatorService.updateSpectator(spectator));
    }

    @PutMapping("/subscription/{planId}")
    public ResponseEntity<Spectator> addOrUpdateSubscription(@RequestBody @Valid Spectator spectator,
                                                     @PathVariable int planId) {
        return ResponseEntity.ok().body(spectatorService.addOrUpdateSubscription(spectator, planId));
    }

    @DeleteMapping("/{spectatorId}")
    public ResponseEntity<String> deleteSpectator (@PathVariable int spectatorId) {
        if (spectatorService.deleteSpectator(spectatorId) != -1) {
            return ResponseEntity.ok().body("Plan with the id: " + spectatorId + " was successfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }
}
