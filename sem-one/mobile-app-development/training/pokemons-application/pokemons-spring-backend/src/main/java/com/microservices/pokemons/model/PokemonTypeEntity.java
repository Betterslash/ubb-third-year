package com.microservices.pokemons.model;

import com.microservices.pokemons.model.enums.shared.PokemonType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "pokemon_type")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PokemonTypeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    private PokemonType typeOne;
    private PokemonType typeTwo;

}
