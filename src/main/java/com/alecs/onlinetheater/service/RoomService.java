package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.repository.RoomRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> listAllRoms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoom(int roomId) {
        return roomRepository.findById(roomId);
    }

    public Room addNewRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) {
        if(roomRepository.existsById(room.getRoomId())) {
            return roomRepository.save(room);
        }

        throw new DataAccessException("Room not found") {};
    }

    public int deleteRoom(int roomId) {
        if (roomRepository.existsById(roomId)) {
            roomRepository.deleteById(roomId);
            return roomId;
        }

        throw new DataAccessException("Room not found") {};
    }
}
