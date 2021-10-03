package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.user.PokemonTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<PokemonTrainer, Long> {
    PokemonTrainer findByUsername(String username);
}
