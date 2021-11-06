package com.microservices.pokemons.mapper;

import com.microservices.pokemons.dto.locations.LocationDto;
import com.microservices.pokemons.model.locations.LocationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationEntity fromDtoToEntity(LocationDto locationDto);
    LocationDto fromEntityToDto(LocationEntity locationEntity);
}
