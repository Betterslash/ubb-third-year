package com.microservices.pokemons.model;

import com.microservices.pokemons.model.enums.shared.PokemonType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany
    public List<PokemonEntity> pokemons;
}
