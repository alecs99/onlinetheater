package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.service.PlayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/play")
public class PlayController {
    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @GetMapping
    public ResponseEntity<List<Play>> listPlays(@RequestParam Optional<String> genre) {
        List<Play> result = (genre.isPresent()) ? playService.listAllPlaysByGenre(genre.get())
                : playService.listAllPlays();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping ("/{playId}")
    public ResponseEntity<Play> listPlayById(@PathVariable int playId) {
        return ResponseEntity.ok().body(playService.findPlay(playId));
    }

    @PostMapping
    public ResponseEntity<Play> addPlay(@RequestBody @Valid Play play, @RequestParam int roomId, @RequestParam int playwrightId) {
        return ResponseEntity.ok().body(playService.addNewPlay(play, roomId, playwrightId));
    }

    @PutMapping
    public ResponseEntity<Play> editPlay(@RequestBody @Valid Play play, @RequestParam int roomId, @RequestParam int playwrightId) {
        return ResponseEntity.ok().body(playService.updatePlay(play, roomId, playwrightId));
    }

    @DeleteMapping("/{playId}")
    public ResponseEntity<String> deletePlay (@PathVariable int playId) {
        if (playService.deletePlay(playId) != -1) {
            return ResponseEntity.ok().body("Play with the id: " + playId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }
}
