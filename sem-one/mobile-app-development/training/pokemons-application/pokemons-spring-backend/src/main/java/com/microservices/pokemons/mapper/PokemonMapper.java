package com.microservices.pokemons.mapper;

import com.microservices.pokemons.dto.PokemonDto;
import com.microservices.pokemons.model.PokemonEntity;
import com.microservices.pokemons.model.PokemonTypeEntity;
import com.microservices.pokemons.repository.PokemonRepository;
import com.microservices.pokemons.repository.PokemonTypesRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    default Long getIdFromId(PokemonEntity pokemonEntity){
        if(pokemonEntity != null) {
            return pokemonEntity.getPokemonId();
        }else{
            return null;
        }
    }

    @Mapping(target = "id", source = "pokemonId")
    PokemonDto fromEntityToDto(PokemonEntity entity);

    default PokemonEntity fromDtoToEntity(PokemonDto dto,
                                          PokemonRepository repository,
                                          PokemonTypesRepository pokemonTypesRepository){
        var types = pokemonTypesRepository
                .findByTypeOneAndTypeTwo(dto.getTypes().getTypeOne(), dto.getTypes().getTypeTwo())
                .orElseGet(() -> pokemonTypesRepository
                        .save(new PokemonTypeEntity(null, dto.getTypes()
                                .getTypeOne(), dto.getTypes()
                                .getTypeTwo(), null)));


            PokemonEntity evolvesFrom = null;
            if (dto.getEvolvesFrom() != null) {
                evolvesFrom = repository.getById(dto.getEvolvesFrom());
            }
            return PokemonEntity.builder()
                    .name(dto.getName())
                    .evolvesFrom(evolvesFrom)
                    .types(types)
                    .registeredAt(LocalDate.parse(dto.getRegisteredAt()))
                    .hasShiny(dto.isHasShiny())
                    .catchRate(dto.getCatchRate())
                    .build();

    }
}
