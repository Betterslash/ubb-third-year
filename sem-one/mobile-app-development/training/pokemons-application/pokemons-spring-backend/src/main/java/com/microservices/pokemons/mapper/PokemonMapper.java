package com.microservices.pokemons.mapper;

import com.microservices.pokemons.dto.pokemons.PokemonDto;
import com.microservices.pokemons.model.locations.LocationEntity;
import com.microservices.pokemons.model.pokemons.PokemonEntity;
import com.microservices.pokemons.model.pokemons.PokemonTypeEntity;
import com.microservices.pokemons.repository.PokemonRepository;
import com.microservices.pokemons.repository.PokemonTypesRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
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
            LocationEntity location = null;
            if(dto.getLocation() != null){
                location = LocationEntity
                        .builder()
                        .id(dto.getLocation().getId())
                        .latitude(dto.getLocation().getLatitude())
                        .longitude(dto.getLocation().getLongitude())
                        .build();
            }
            return PokemonEntity.builder()
                    .name(dto.getName())
                    .evolvesFrom(evolvesFrom)
                    .types(types)
                    .registeredAt(LocalDate.parse(dto.getRegisteredAt()))
                    .hasShiny(dto.isHasShiny())
                    .catchRate(dto.getCatchRate())
                    .photoPath(dto.getPhotoPath())
                    .location(location)
                    .build();

    }
}
