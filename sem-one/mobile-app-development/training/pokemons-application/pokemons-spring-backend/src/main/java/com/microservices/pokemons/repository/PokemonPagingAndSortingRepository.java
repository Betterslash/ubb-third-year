package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.PokemonEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonPagingAndSortingRepository extends PagingAndSortingRepository<PokemonEntity, Long> {
}
