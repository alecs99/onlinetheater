package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.service.RoomService;
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
@RequestMapping("/room")
@Api(value = "room", description = "In this endpoint are defined requests for the room model " +
        "(list, insert, update or delete plans) ")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "View a list of available rooms ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the plan"),
            @ApiResponse(code = 404, message = "No rooms were found")
    })
    @GetMapping
    public ResponseEntity<List<Room>> listRooms() {
        return ResponseEntity.ok().body(roomService.listAllRoms());
    }

    @ApiOperation(value = "View a room based on a given id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the room"),
            @ApiResponse(code = 404, message = "No room with the given id was found")
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<?> listRoomById(@PathVariable int roomId) {
        Optional<Room> result = roomService.findRoom(roomId);

        if (result.isPresent())
            return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Add a new room ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added the room"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody @Valid Room room) {
        Room savedRoom = roomService.addNewRoom(room);
        return ResponseEntity.created(URI.create("/room/" + savedRoom.getRoomId())).body(savedRoom);
    }

    @ApiOperation(value = "Edit an existing room ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited the room"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PutMapping
    public ResponseEntity<Room> editRoom(@RequestBody @Valid Room room) {
        return ResponseEntity.ok().body(roomService.updateRoom(room));
    }

    @ApiOperation(value = "Delete an existing room ", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the room"),
            @ApiResponse(code = 404, message = "No room with the given id was found")
    })
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        if (roomService.deleteRoom(roomId) != -1) {
            return ResponseEntity.ok().body("Room with the id: " + roomId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();
    }

}
