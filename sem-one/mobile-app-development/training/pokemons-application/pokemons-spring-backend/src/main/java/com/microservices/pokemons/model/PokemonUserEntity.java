package com.microservices.pokemons.model;

import com.microservices.pokemons.model.embeddables.PokemonUserKey;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "pokemon_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonUserEntity implements Serializable {
    @EmbeddedId
    private PokemonUserKey id;

    @ManyToOne
    @MapsId("pokemonId")
    @JoinColumn(name = "pokemon_id")
    PokemonEntity pokemon;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id")
    TrainerEntity trainer;

}
