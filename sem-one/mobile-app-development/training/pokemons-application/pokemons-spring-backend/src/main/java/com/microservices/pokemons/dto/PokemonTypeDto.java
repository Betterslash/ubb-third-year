package com.microservices.pokemons.dto;

import com.microservices.pokemons.shared.PokemonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonTypeDto implements Serializable {
    private PokemonType typeOne;
    private PokemonType typeTwo;
}
