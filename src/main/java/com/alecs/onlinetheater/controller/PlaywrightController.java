package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Playwright;
import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.service.PlaywrightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/playwright")
public class PlaywrightController {
    private final PlaywrightService playwrightService;

    public PlaywrightController(PlaywrightService playwrightService) {
        this.playwrightService = playwrightService;
    }

    @GetMapping
    public ResponseEntity<List<Playwright>> listPlaywrights() {
        return ResponseEntity.ok().body(playwrightService.listAllPlaywrights());
    }

    @PostMapping
    public ResponseEntity<Playwright> addPlaywright(@RequestBody @Valid Playwright playwright) {
        return ResponseEntity.ok().body(playwrightService.addNewPlaywright(playwright));
    }

    @PutMapping
    public ResponseEntity<Playwright> editRoom (@RequestBody @Valid Playwright playwright) {
        return ResponseEntity.ok().body(playwrightService.updatePlaywright(playwright));
    }

    @DeleteMapping("/{playwrightId}")
    public ResponseEntity<String> deleteRoom (@PathVariable int playwrightId) {
        if (playwrightService.deletePlaywright(playwrightId) != -1) {
            return ResponseEntity.ok().body("Playwright with the id: " + playwrightId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();

    }
}
