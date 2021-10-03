package com.microservices.pokemons.shared;

import lombok.Getter;

@Getter
public enum PokemonType {
    //1
    FIRE("type:FIRE"),
    //2
    WATER("type:WATER"),
    //3
    GRASS("type:GRASS"),
    //4
    DARK("type:DARK"),
    //5
    FAIRY("type:FAIRY"),
    //6
    DRAGON("type:DRAGON"),
    GHOST("type:GHOST"),
    FIGHTING("type:FIGHTING"),
    PSYCHIC("type:PSYCHIC"),
    GROUND("type:GROUND"),
    ROCK("type:ROCK"),
    ICE("type:ICE"),
    NORMAL("type:NORMAL"),
    POISON("type:POISON"),
    FLYING("type:FLYING"),
    BUG("type:BUG"),
    ELECTRIC("type:ELECTRIC"),
    STEEL("type:STEEL");

    private final String value;

    PokemonType(String value) {
        this.value = value;
    }

}
