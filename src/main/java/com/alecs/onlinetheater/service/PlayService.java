package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.model.Playwright;
import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.repository.PlayRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayService {
    private final PlayRepository playRepository;
    private final RoomService roomService;
    private final PlaywrightService playwrightService;

    public PlayService(PlayRepository playRepository, RoomService roomService, PlaywrightService playwrightService) {
        this.playRepository = playRepository;
        this.roomService = roomService;
        this.playwrightService = playwrightService;
    }

    public List<Play> listAllPlays () {
        return playRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Play::getPlayName))
                .collect(Collectors.toList()); }

    public List<Play> listAllPlaysByGenre(String genre) {
        return playRepository.findAll()
                .stream()
                .filter(p -> p.getGenre().toLowerCase().equals(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Play findPlay(int playId) {
        return playRepository.findById(playId)
                .orElseThrow(() -> new DataAccessException("Plan with given id not found") {});
    }

    public Play addNewPlay(Play play, int roomId, int playwrightId) {
        Room requestedRoom = roomService.findRoom(roomId)
                .orElseThrow(() -> new DataAccessException("Room with given id not found") {});

        Playwright requestedPlaywright = playwrightService.findPlaywright(playwrightId)
                .orElseThrow(() -> new DataAccessException("Playwright with given id not found") {});

        play.setPlayRoom(requestedRoom);
        play.setPlaywright(requestedPlaywright);

        return playRepository.save(play);
    }

    public Play updatePlay(Play play, int roomId, int playwrightId) {
        if(playRepository.existsById(play.getPlayId())) {
            Room requestedRoom = roomService.findRoom(roomId)
                    .orElseThrow(() -> new DataAccessException("Room with given id not found") {});

            Playwright requestedPlaywright = playwrightService.findPlaywright(playwrightId)
                    .orElseThrow(() -> new DataAccessException("Playwright with given id not found") {});

            play.setPlayRoom(requestedRoom);
            play.setPlaywright(requestedPlaywright);

            return playRepository.save(play);
        }

        throw new DataAccessException("Play not found") {};
    }

    public int deletePlay(int playId) {
        if (playRepository.existsById(playId)) {
            playRepository.deleteById(playId);
            return playId;
        }

        throw new DataAccessException("Play not found") {};
    }
}
