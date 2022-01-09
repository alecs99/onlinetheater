package com.alecs.onlinetheater.repository;

import com.alecs.onlinetheater.model.Playwright;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaywrightRepository extends JpaRepository<Playwright, Integer> {
}
