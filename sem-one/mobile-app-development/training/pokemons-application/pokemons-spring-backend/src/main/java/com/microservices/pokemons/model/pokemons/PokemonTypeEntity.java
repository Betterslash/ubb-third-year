package com.microservices.pokemons.model.pokemons;

import com.microservices.pokemons.model.BaseEntity;
import com.microservices.pokemons.shared.PokemonType;
import lombok.*;

import javax.persistence.Entity;

@Entity(name = "pokemon_type")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PokemonTypeEntity extends BaseEntity {
    private PokemonType typeOne;
    private PokemonType typeTwo;
}
