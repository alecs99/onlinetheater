package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.service.PlayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/play")
public class PlayController {
    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @GetMapping
    public ResponseEntity<List<Play>> listPlays() {
        return ResponseEntity.ok().body(playService.listAllPlays());
    }

    @PostMapping
    public ResponseEntity<Play> addPlay(@RequestBody Play play, @RequestParam int roomId, @RequestParam int playwrightId) {
        return ResponseEntity.ok().body(playService.addNewPlay(play, roomId, playwrightId));
    }

    @PutMapping
    public ResponseEntity<Play> editPlay(@RequestBody Play play, @RequestParam int roomId, @RequestParam int playwrightId) {
        return ResponseEntity.ok().body(playService.updatePlay(play, roomId, playwrightId));
    }
}
