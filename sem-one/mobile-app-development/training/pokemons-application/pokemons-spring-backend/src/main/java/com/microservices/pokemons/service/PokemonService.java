package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.PokemonDto;

import java.util.List;

public interface PokemonService {
    List<PokemonDto> getAllPokemons();
    PokemonDto insertOne(PokemonDto pokemonDto);
    PokemonDto deleteOne(Long id);
    PokemonDto getOneById(Long id);
    List<PokemonDto> getUserPokemons();
    PokemonDto releaseOne(Long id);
    PokemonDto catchOne(Long id);
}
