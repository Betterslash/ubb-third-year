package com.microservices.pokemons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class PokemonDto extends BaseDto{
    private String name;
    private PokemonTypeDto types;
    private Long evolvesFrom;
}
