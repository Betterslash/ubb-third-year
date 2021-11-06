package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.pokemons.PokemonUserEntity;
import com.microservices.pokemons.model.embeddables.PokemonUserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonUserRepository extends JpaRepository<PokemonUserEntity, PokemonUserKey> {
}
