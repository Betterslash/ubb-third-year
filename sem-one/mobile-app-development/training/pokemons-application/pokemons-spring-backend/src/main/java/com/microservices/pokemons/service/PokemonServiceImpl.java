package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.PokemonDto;
import com.microservices.pokemons.exception.PokemonServiceException;
import com.microservices.pokemons.mapper.PokemonMapper;
import com.microservices.pokemons.repository.PokemonRepository;
import com.microservices.pokemons.repository.PokemonTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final PokemonMapper pokemonMapper;
    private final PokemonRepository pokemonRepository;
    private final PokemonTypesRepository pokemonTypesRepository;

    @Override
    public List<PokemonDto> getAllPokemons() {
        return pokemonRepository.findAll()
                .stream()
                .map(pokemonMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PokemonDto insertOne(PokemonDto pokemonDto) {
        return this.pokemonMapper.fromEntityToDto(this.pokemonRepository.save(this.pokemonMapper.fromDtoToEntity(pokemonDto, pokemonRepository, pokemonTypesRepository)));
    }

    @Override
    public PokemonDto deleteOne(Long id) {
        var pokemon = this.pokemonRepository.findById(id);
        try {
            this.pokemonRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new PokemonServiceException(e.getMessage());
        }
        if(pokemon.isEmpty()){
            throw new PokemonServiceException(String.format("Pokemon with id %d not found !!", id));
        }else{
            return this.pokemonMapper.fromEntityToDto(pokemon.get());
        }
    }

    @Override
    public PokemonDto getOneById(Long id){
        var pokemon = this.pokemonRepository.getById(id);
        return this.pokemonMapper.fromEntityToDto(pokemon);
    }
}
