package com.microservices.pokemons.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "pokemons")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokemonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pokemonId;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "typeId")
    private PokemonTypeEntity types;

    @OneToOne
    private PokemonEntity evolvesFrom;

    @OneToMany(mappedBy = "pokemon", targetEntity = PokemonUserEntity.class)
    private Set<TrainerEntity> trainers;
}
