package com.microservices.pokemons.dto.files;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
}
