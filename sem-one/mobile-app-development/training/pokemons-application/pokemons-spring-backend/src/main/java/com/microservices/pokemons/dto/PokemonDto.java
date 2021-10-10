package com.microservices.pokemons.dto;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDto extends BaseDto{
    private String name;
    private PokemonTypeDto types;
    private Long evolvesFrom;
    private boolean hasShiny;
    private String registeredAt;
}
