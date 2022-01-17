package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Playwright;
import com.alecs.onlinetheater.service.PlaywrightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playwright")
@Api(value = "playwright", description = "In this endpoint are defined requests for the playwright model " +
        "(list, insert, update or delete plans) ")
public class PlaywrightController {
    private final PlaywrightService playwrightService;

    public PlaywrightController(PlaywrightService playwrightService) {
        this.playwrightService = playwrightService;
    }

    @ApiOperation(value = "View a list of available playwrights ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the plan"),
            @ApiResponse(code = 404, message = "No playwrights were found")
    })
    @GetMapping
    public ResponseEntity<List<Playwright>> listplaywrights() {
        return ResponseEntity.ok().body(playwrightService.listAllPlaywrights());
    }

    @ApiOperation(value = "Add a new playwright ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added the playwright"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PostMapping
    public ResponseEntity<Playwright> addplaywright(@RequestBody @Valid Playwright playwright) {
        Playwright savedPlaywright = playwrightService.addNewPlaywright(playwright);
        return ResponseEntity.created(URI.create("/playwright/" + savedPlaywright.getId()))
                .body(savedPlaywright);
    }

    @ApiOperation(value = "Edit an existing playwright ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited the playwright"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PutMapping
    public ResponseEntity<Playwright> editRoom(@RequestBody @Valid Playwright playwright) {
        return ResponseEntity.ok().body(playwrightService.updatePlaywright(playwright));
    }

    @ApiOperation(value = "Delete an existing playwright ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the playwright"),
            @ApiResponse(code = 404, message = "No playwright with the given id was found")
    })
    @DeleteMapping("/{playwrightId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int playwrightId) {
        if (playwrightService.deletePlaywright(playwrightId) != -1) {
            return ResponseEntity.ok().body("playwright with the id: " + playwrightId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();

    }
}
