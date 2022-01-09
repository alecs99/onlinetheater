package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Play;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRepository extends JpaRepository<Play, Integer> {
}
