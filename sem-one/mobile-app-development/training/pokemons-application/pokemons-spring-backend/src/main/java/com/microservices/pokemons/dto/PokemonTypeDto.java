package com.microservices.pokemons.dto;

import com.microservices.pokemons.model.enums.shared.PokemonType;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PokemonTypeDto implements Serializable {
    private PokemonType typeOne;
    private PokemonType typeTwo;
}
