package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.*;
import com.alecs.onlinetheater.repository.SpectatorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecatatorServiceTest {
    @InjectMocks
    private SpectatorService spectatorService;
    @Mock
    private SpectatorRepository spectatorRepository;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private PlayService playService;

    @Test
    @DisplayName("Get spectator happy flow")
    void getSpectator() {
        int spectatorId = 1;
        Spectator spectator = new Spectator("Test");
        when(spectatorRepository.findById(spectatorId))
                .thenReturn(Optional.of(spectator));

        Optional<Spectator> result = spectatorService.getSpectatorDetails(spectatorId);

        assertNotNull(result);
        assertEquals(spectator.getUsername(), result.get().getUsername());
    }

    @Test
    @DisplayName("Running add new spectator in a happy flow")
    void addNewSpectatorHappyFlow() {
        Spectator spectator = new Spectator("Test");
        when(spectatorRepository.save(spectator)).thenReturn(spectator);
        Spectator result = spectatorService.addNewSpectator(spectator);

        assertNotNull(result);
        assertEquals(spectator.getUsername(), result.getUsername());
    }

    @Test
    @DisplayName("Running watch play in a happy flow")
    void watchSpectatorPlaysHappyFlow() {
        //arrange
        int spectatorId = 1;
        int playId = 2;

        Room playRoom = new Room("name", "type");
        Plan subscriptionPlan = new Plan("Basic", "description", 100);
        Spectator spectator = new Spectator("spectator");
        Play requestedPlay = new Play("Play name", "genre", "description", 120);
        Subscription spectatorSubscription = new Subscription
                (LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        List<Play> playList = new ArrayList<>(Arrays.asList(
                new Play("Play1", "genre", "description", 120),
                new Play("Play2", "genre", "description", 120),
                new Play("Play3", "genre", "description", 120)
        ));

        spectator.setPlayList(playList);

        requestedPlay.setPlayRoom(playRoom);
        spectatorSubscription.setSubscriptionPlan(subscriptionPlan);

        spectator.setSubscription(spectatorSubscription);

        when(spectatorRepository.findById(spectatorId)).thenReturn(Optional.of(spectator));
        when(playService.findPlay(playId)).thenReturn(requestedPlay);
        when(subscriptionService.checkValidSubscription(spectatorSubscription)).thenReturn(false);

        when(spectatorRepository.save(spectator)).thenReturn(spectator);

        Spectator result = spectatorService.watchPlay(spectatorId, playId);

        assertNotNull(result);
        assertEquals(spectator.getUsername(), result.getUsername());
        assertEquals(4, result.getPlayList().size());
    }

    @Test
    @DisplayName("Running watch play when no spectator was found in a bad flow")
    void watchPlayNoSpectatorBadFlow() {
        int spectatorId = 1;
        int playId = 2;

        when(spectatorRepository.findById(spectatorId)).thenReturn(Optional.empty());

        try {
            Spectator result = spectatorService.watchPlay(spectatorId, playId);
        } catch (DataAccessException e) {
            assertEquals("Spectator not found", e.getMessage());
        }

    }

    @Test
    @DisplayName("Running watch play when no play was found in a bad flow")
    void watchPlayNoPlayBadFlow() {
        int spectatorId = 1;
        int playId = 2;
        Spectator spectator = new Spectator("spectator");


        when(spectatorRepository.findById(spectatorId)).thenReturn(Optional.of(spectator));
        when(playService.findPlay(playId))
                .thenThrow(new DataAccessException("Play not found") {
                });

        try {
            Spectator result = spectatorService.watchPlay(spectatorId, playId);
        } catch (DataAccessException e) {
            assertEquals("Play not found", e.getMessage());
        }

    }

    @Test
    @DisplayName("Running add or  update spectator  subscription in a happy flow")
    void addOrUpdateSpectatorHappyFlow() {
        int planId = 1;
        Plan plan = new Plan(1, "name", "description", 100);
        Spectator spectator = new Spectator("spectator");

        when(spectatorRepository.save(spectator)).thenReturn(spectator);

        Spectator result = spectatorService.addOrUpdateSubscription(spectator, planId);

        assertNotNull(result);
        assertEquals(spectator.getUsername(), result.getUsername());

    }

    @Test
    @DisplayName("Running update spectator in a happy flow")
    void updateSpectatorHappyFlow() {
        Spectator spectator = new Spectator("spectator");

        when(spectatorRepository.existsById(spectator.getSpectatorId())).thenReturn(true);
        when(spectatorRepository.save(spectator)).thenReturn(spectator);

        Spectator result = spectatorService.updateSpectator(spectator);

        assertEquals(spectator.getUsername(), result.getUsername());

    }

    @Test
    @DisplayName("Running update spectator in a bad flow")
    void updateSpectatorBadFlow() {
        Spectator spectator = new Spectator("spectator");

        when(spectatorRepository.existsById(spectator.getSpectatorId()))
                .thenReturn(false);

        try {
            Spectator result = spectatorService.updateSpectator(spectator);
        } catch (DataAccessException ex) {
            assertEquals("Spectator not found", ex.getMessage());
        }
    }

    @Test
    @DisplayName("Running delete spectator in a happy flow")
    void deleteSpectatorHappyFlow() {
        int spectatorId = 1;
        when(spectatorRepository.existsById(spectatorId)).thenReturn(true);
        doNothing().when(spectatorRepository).deleteById(spectatorId);

        int result = spectatorService.deleteSpectator(spectatorId);

        assertEquals(spectatorId, result);
        verify(spectatorRepository, times(1))
                .deleteById(spectatorId);

    }

    @Test
    @DisplayName("Running delete spectator in a bad flow")
    void deleteSpectatorBadFlow() {
        int spectatorId = 1;
        when(spectatorRepository.existsById(spectatorId)).thenReturn(false);

        try {
            int result = spectatorService.deleteSpectator(spectatorId);
        } catch (DataAccessException ex) {
            assertEquals("Spectator not found", ex.getMessage());
        }
    }

}
