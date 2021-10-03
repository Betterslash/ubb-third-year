package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.pokemons.PokemonTypeEntity;
import com.microservices.pokemons.shared.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonTypesRepository extends JpaRepository<PokemonTypeEntity, Long> {
    PokemonTypeEntity getByTypeOneAndTypeTwo(PokemonType typeOne, PokemonType typeTwo);
}
