package com.microservices.pokemons.dto.locations;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private String id;
    private Double latitude;
    private Double longitude;
}
