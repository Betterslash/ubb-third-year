package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.pokemons.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Long> {
    Optional<PokemonEntity> findByEvolvesFrom(PokemonEntity evolvesFrom);
}

