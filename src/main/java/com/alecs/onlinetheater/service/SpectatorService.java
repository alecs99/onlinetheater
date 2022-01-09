package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Plan;
import com.alecs.onlinetheater.model.Spectator;
import com.alecs.onlinetheater.model.Subscription;
import com.alecs.onlinetheater.repository.SpectatorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Spectator addNewSpectator(Spectator spectator) {
        return spectatorRepository.save(spectator);
    }

    public Spectator addSubscription(Spectator spectator, int planId) {
        Subscription subscription = subscriptionService.addNewSubscription(spectator, planId, LocalDateTime.now());
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
