package com.microservices.pokemons.dto;

import io.micrometer.core.lang.Nullable;
import lombok.*;

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
    private Long catchRate;
    @Nullable
    private Boolean deletionMark;
}
