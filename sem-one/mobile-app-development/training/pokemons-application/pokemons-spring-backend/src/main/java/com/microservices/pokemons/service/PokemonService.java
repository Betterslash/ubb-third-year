package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.pokemons.PokemonDto;
import com.microservices.pokemons.dto.pokemons.PokemonUserDto;

import java.util.List;

public interface PokemonService {
    List<PokemonDto> getAllPokemons();
    PokemonDto insertOne(PokemonDto pokemonDto);
    PokemonDto deleteOne(Long id);
    PokemonDto getOneById(Long id);
    List<PokemonDto> getUserPokemons();
    PokemonDto releaseOne(Long id);
    PokemonDto catchOne(PokemonUserDto id);
    List<PokemonDto> getAllPaginated(Long size, Long from);
    PokemonDto updateOne(Long id, PokemonDto pokemonDto);
    List<PokemonDto> insertMultiplePokemons(List<PokemonDto> pokemons);
}
