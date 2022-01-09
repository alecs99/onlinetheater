package com.alecs.onlinetheater.controller;

import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> listRooms() {
        return ResponseEntity.ok().body(roomService.listAllRoms());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> listRoomById(@PathVariable int roomId) {
        Optional<Room> result = roomService.findRoom(roomId);

        if (result.isPresent())
            return ResponseEntity.ok().body(result);

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Room> addRoom (@RequestBody @Valid Room room) {
        Room savedRoom = roomService.addNewRoom(room);
        return ResponseEntity.created(URI.create(savedRoom.getRoomId().toString())).body(savedRoom);
    }

    @PutMapping
    public ResponseEntity<Room> editRoom (@RequestBody @Valid Room room) {
        return ResponseEntity.ok().body(roomService.updateRoom(room));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom (@PathVariable int roomId) {
        if (roomService.deleteRoom(roomId) != -1) {
            return ResponseEntity.ok().body("Room with the id: " + roomId + " was succesfully deleted!");
        }
        return ResponseEntity.notFound().build();

    }

}
