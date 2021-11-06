package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.notification.NotificationDto;

import java.util.List;

public interface NotificationService {
    void broadcast(NotificationDto notification);
    void notifyUser(NotificationDto notificationDto);
    void multipleBrodacastToUsers(List<NotificationDto> notificationDtos);
}
