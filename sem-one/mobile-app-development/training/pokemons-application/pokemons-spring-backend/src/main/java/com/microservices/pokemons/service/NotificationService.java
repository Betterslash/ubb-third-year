package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.NotificationDto;

public interface NotificationService {
    void broadcast(NotificationDto notification);
}
