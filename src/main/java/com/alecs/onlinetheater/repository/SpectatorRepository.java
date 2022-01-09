package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Spectator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpectatorRepository extends JpaRepository<Spectator, Integer> {
}
