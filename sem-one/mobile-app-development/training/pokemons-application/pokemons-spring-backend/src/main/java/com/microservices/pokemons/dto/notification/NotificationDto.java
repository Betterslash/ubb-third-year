package com.microservices.pokemons.dto.notification;

import com.microservices.pokemons.dto.ActionType;
import com.microservices.pokemons.dto.pokemons.PokemonDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class NotificationDto implements Serializable {
    private final UUID id;
    private final ActionType action;
    private final PokemonDto body;

    private NotificationDto(ActionType actionType, PokemonDto body) {
        this.id = java.util.UUID.randomUUID();
        this.action = actionType;
        this.body = body;
    }

    public final static class Builder{
        public static NotificationDto build(ActionType actionType, PokemonDto dto){
            return new NotificationDto(actionType, dto);
        }
    }
}
