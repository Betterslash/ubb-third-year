package com.microservices.pokemons.dto.pokemons;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonUserDto implements Serializable {
    private Long pokemonId;
    private String pokemonName;
    private Long caughtNumber;
}
