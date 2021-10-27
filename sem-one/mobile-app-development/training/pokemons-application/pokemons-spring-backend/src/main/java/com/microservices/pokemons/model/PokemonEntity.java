package com.microservices.pokemons.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    private boolean hasShiny;

    private LocalDate registeredAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "typeId")
    private PokemonTypeEntity types;

    @OneToOne(cascade = CascadeType.ALL)
    private PokemonEntity evolvesFrom;


    @OneToMany(mappedBy = "pokemon",
            targetEntity = PokemonUserEntity.class,
            cascade = CascadeType.ALL,
    orphanRemoval = true)
    private Set<TrainerEntity> trainers;

    private Long catchRate;
}
