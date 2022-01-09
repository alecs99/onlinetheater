package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Play;
import com.alecs.onlinetheater.model.Playwright;
import com.alecs.onlinetheater.model.Room;
import com.alecs.onlinetheater.repository.PlayRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Play> listAllPlays () { return playRepository.findAll(); }

    public Optional<Play> findPlay(int playId) {
        return playRepository.findById(playId);
    }

    public Play addNewPlay(Play play, int roomId, int playwrightId) {
        Optional<Room> requestedRoom = roomService.findRoom(roomId);

        if(!requestedRoom.isPresent())
            throw new DataAccessException("Room with given id not found") {};

        Optional<Playwright> requestedPlaywright = playwrightService.findPlaywright(playwrightId);

        if(!requestedPlaywright.isPresent())
            throw new DataAccessException("Playwright with given id not found") {};

        play.setPlayRoom(requestedRoom.get());
        play.setPlaywright(requestedPlaywright.get());

        return playRepository.save(play);
    }

    public Play updatePlay(Play play, int roomId, int playwrightId) {
        if(playRepository.existsById(play.getPlayId())) {
            Optional<Room> requestedRoom = roomService.findRoom(roomId);

            if(!requestedRoom.isPresent())
                throw new DataAccessException("Room with given id not found") {};

            Optional<Playwright> requestedPlaywright = playwrightService.findPlaywright(playwrightId);

            if(!requestedPlaywright.isPresent())
                throw new DataAccessException("Playwright with given id not found") {};

            play.setPlayRoom(requestedRoom.get());
            play.setPlaywright(requestedPlaywright.get());

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
