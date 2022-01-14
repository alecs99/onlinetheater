package com.alecs.onlinetheater.service;

import com.alecs.onlinetheater.model.Spectator;
import com.alecs.onlinetheater.repository.SpectatorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
    @DisplayName("Running add new spectator in a happy flow")
    void addNewSpectatorHappyFlow() {
        Spectator spectator = new Spectator("Test");
        when(spectatorRepository.save(spectator)).thenReturn(spectator);
        Spectator result = spectatorService.addNewSpectator(spectator);

        assertNotNull(result);
        assertEquals(spectator.getUsername(), result.getUsername());
    }
}
