package com.microservices.pokemons.mapper;

import com.microservices.pokemons.dto.locations.LocationDto;
import com.microservices.pokemons.model.locations.LocationEntity;

public abstract class LocationConverter {
    public static LocationEntity convertFromDtoToEntity(LocationDto dto){
        return LocationEntity
                .builder()
                .id(dto.getId())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }

    public static LocationDto convertFromEntityToDto(LocationEntity entity){
        return LocationDto
                .builder()
                .id(entity.getId())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
