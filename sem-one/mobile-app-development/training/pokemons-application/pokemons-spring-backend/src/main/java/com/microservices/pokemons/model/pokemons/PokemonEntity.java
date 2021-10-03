package com.microservices.pokemons.model.pokemons;

import com.microservices.pokemons.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity(name = "pokemons")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokemonEntity extends BaseEntity {

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private PokemonTypeEntity types;

    @OneToOne
    private PokemonEntity evolvesFrom;

}
