package com.microservices.pokemons.model.enums.shared;

import lombok.Getter;

@Getter
public enum PokemonType {
    //0
    FIRE("type:FIRE"),
    //1
    WATER("type:WATER"),
    //2
    GRASS("type:GRASS"),
    //3
    DARK("type:DARK"),
    //4
    FAIRY("type:FAIRY"),
    //5
    DRAGON("type:DRAGON"),
    //6
    GHOST("type:GHOST"),
    //7
    FIGHTING("type:FIGHTING"),
    //8
    PSYCHIC("type:PSYCHIC"),
    //9
    GROUND("type:GROUND"),
    //10
    ROCK("type:ROCK"),
    //11
    ICE("type:ICE"),
    //12
    NORMAL("type:NORMAL"),
    //13
    POISON("type:POISON"),
    //14
    FLYING("type:FLYING"),
    //15
    BUG("type:BUG"),
    //16
    ELECTRIC("type:ELECTRIC"),
    //17
    STEEL("type:STEEL");

    private final String value;

    PokemonType(String value) {
        this.value = value;
    }

}
