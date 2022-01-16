package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.service.PlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/play")
//@Api(value = "play", description = "In this endpoint are defined requests for the play model " +
//        "(list, insert, update or delete plays) ")
public class PlayController {
    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

//    @ApiOperation(value = "View a list of available plays or add genre filter in order to " +
//            "show plays based on that type of play. ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved the plan"),
//            @ApiResponse(code = 404, message = "No plays were found")
//    })
    @GetMapping
    public ResponseEntity<List<Play>> listPlays(@RequestParam Optional<String> genre) {
        List<Play> result = (genre.isPresent()) ? playService.listAllPlaysByGenre(genre.get())
                : playService.listAllPlays();
        return ResponseEntity.ok().body(result);
    }

//    @ApiOperation(value = "View a play based on a given id", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved the play"),
//            @ApiResponse(code = 404, message = "No play with the given id was found")
//    })
    @GetMapping("/{playId}")
    public ResponseEntity<Play> listPlayById(@PathVariable int playId) {
        return ResponseEntity.ok().body(playService.findPlay(playId));
    }

//    @ApiOperation(value = "Add a new play ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully added the play"),
//            @ApiResponse(code = 404, message = "Resource not found")
//    })
    @PostMapping
    public ResponseEntity<Play> addPlay(@RequestBody @Valid Play play, @RequestParam int roomId, @RequestParam int playwrightId) {
        return ResponseEntity.ok().body(playService.addNewPlay(play, roomId, playwrightId));
    }

//    @ApiOperation(value = "Edit an existing play ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully edited the play"),
//            @ApiResponse(code = 404, message = "Resource not found")
//    })
    @PutMapping
    public ResponseEntity<Play> editPlay(@RequestBody @Valid Play play, @RequestParam int roomId, @RequestParam int playwrightId) {
        return ResponseEntity.ok().body(playService.updatePlay(play, roomId, playwrightId));
    }

//    @ApiOperation(value = "Delete an existing play ", response = ResponseEntity.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully deleted the play"),
//            @ApiResponse(code = 404, message = "No play with the given id was found")
//    })
    @DeleteMapping("/{playId}")
    public ResponseEntity<String> deletePlay(@PathVariable int playId) {
        if (playService.deletePlay(playId) != -1) {
            return ResponseEntity.ok().body("Play with the id: " + playId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }
}
