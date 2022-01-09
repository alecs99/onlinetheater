package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
