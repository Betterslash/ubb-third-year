package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.NotificationDto;
import com.microservices.pokemons.model.TrainerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void broadcast(NotificationDto notification) {
        simpMessagingTemplate.convertAndSend("/notify", notification);
    }

    @Override
    public void notifyUser(NotificationDto notificationDto) {
        var user = (TrainerEntity)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        var username = user.getUsername();
        simpMessagingTemplate.convertAndSend(String.format("/myPokemons/%s", username), notificationDto);
    }

    @Override
    public void multipleBrodacastToUsers(List<NotificationDto> notificationDtos) {
        notificationDtos.forEach(e -> simpMessagingTemplate.convertAndSend("/notify", e));
    }


}
