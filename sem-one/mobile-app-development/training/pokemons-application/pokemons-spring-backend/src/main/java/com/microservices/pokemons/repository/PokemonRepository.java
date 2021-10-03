package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.pokemons.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Long> {
}
