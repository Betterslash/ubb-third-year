package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.pokemons.PokemonTypeEntity;
import com.microservices.pokemons.model.enums.shared.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonTypesRepository extends JpaRepository<PokemonTypeEntity, Long> {
    Optional<PokemonTypeEntity> findByTypeOneAndTypeTwo(PokemonType typeOne, PokemonType typeTwo);
}
