package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Playwright;
import com.alecs.onlinetheater.service.PlaywrightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Playwright> addPlaywright(@RequestBody Playwright playwright) {
        return ResponseEntity.ok().body(playwrightService.addNewPlaywright(playwright));
    }
}
