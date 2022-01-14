package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Playwright;
import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.repository.PlaywrightRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class PlaywrightService {
    private final PlaywrightRepository playwrightRepository;

    public PlaywrightService(PlaywrightRepository playwrightRepository) {
        this.playwrightRepository = playwrightRepository;
    }

    public List<Playwright> listAllPlaywrights() {
        return playwrightRepository.findAll();
    }

    public Optional<Playwright> findPlaywright(int playwrightId) {
        return playwrightRepository.findById(playwrightId);
    }

    public Playwright addNewPlaywright(Playwright playwright) {
        return playwrightRepository.save(playwright);
    }

    public Playwright updatePlaywright(Playwright playwright) {
        if(playwrightRepository.existsById(playwright.getId())) {
            return playwrightRepository.save(playwright);
        }

        throw new DataAccessException("Playwright not found") {};
    }

    public int deletePlaywright(int playwrightId) {
        if (playwrightRepository.existsById(playwrightId)) {
            playwrightRepository.deleteById(playwrightId);
            return playwrightId;
        }

        throw new DataAccessException("Playwright not found") {};
    }
    
}
