package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void broadcast(NotificationDto notification) {
        simpMessagingTemplate.convertAndSend("/notify", notification);
    }
}
