package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.exceptions.NoSubscriptionException;
import com.alecs.onlinetheater.exceptions.SubscriptionAccessException;
import com.alecs.onlinetheater.model.*;
import com.alecs.onlinetheater.repository.SpectatorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpectatorService {
    private final SpectatorRepository spectatorRepository;
    private final SubscriptionService subscriptionService;
    private final PlayService playService;

    public SpectatorService(SpectatorRepository spectatorRepository, SubscriptionService subscriptionService, PlayService playService) {
        this.spectatorRepository = spectatorRepository;
        this.subscriptionService = subscriptionService;
        this.playService = playService;
    }

    public Optional<Spectator> getSpectatorDetails(int id) {
        return spectatorRepository.findById(id);
    }

    public List<Play> getSpectatorPlays(int spectatorId) {
        Spectator dbSpectator = spectatorRepository.findById(spectatorId)
                .orElseThrow(() -> new DataAccessException("Spectator not found") {});
        return dbSpectator.getPlayList()
                .stream()
                .sorted(Comparator.comparing(Play::getPlayName))
                .collect(Collectors.toList());
    }

    public Spectator addNewSpectator(Spectator spectator) {
        return spectatorRepository.save(spectator);
    }

    public Spectator watchPlay(int spectatorId, int playId) {
        Spectator spectator = spectatorRepository.findById(spectatorId)
                .orElseThrow(() -> new DataAccessException("Spectator not found") {});

        Play requestedPlay = playService.findPlay(playId);

        Room playRoom = requestedPlay.getPlayRoom();

        Subscription spectatorSubscription = spectator.getSubscription();

        if(spectatorSubscription == null) {
            throw new NoSubscriptionException("Spectator don't have any subscription! Please get one!");
        }

        String spectatorPlan = spectatorSubscription.getSubscriptionPlan().getPlanName();

        // Check if Spectator have valid Subscription
        if (subscriptionService.checkValidSubscription(spectatorSubscription))
            throw new NoSubscriptionException("Spectator have the subscription expired! Please renew it!");

        // Spectator try to access a room not available for his subscription
        if (!spectatorPlan.equals("Premium") && playRoom.getRoomType().equals("Premium")){
                throw new SubscriptionAccessException("Your subscription doesn't allow accessing this play!!");
        } else {
            List<Play> playList = spectator.getPlayList();
            playList.add(requestedPlay);
            spectator.setPlayList(playList);

            return spectatorRepository.save(spectator);
        }
    }

    public Spectator addOrUpdateSubscription(Spectator spectator, int planId) {
        Subscription subscription = subscriptionService.addOrUpdateSubscription(spectator, planId, LocalDateTime.now());
        spectator.setSubscription(subscription);
        return spectatorRepository.save(spectator);
    }

    public Spectator updateSpectator(Spectator spectator) {
        if(spectatorRepository.existsById(spectator.getSpectatorId())) {
            return spectatorRepository.save(spectator);
        }

        throw new DataAccessException("Spectator not found") {};
    }

    public int deleteSpectator(int spectatorId) {
        if (spectatorRepository.existsById(spectatorId)) {
            spectatorRepository.deleteById(spectatorId);
            return spectatorId;
        }

        throw new DataAccessException("Spectator not found") {};
    }
}
