package com.microservices.pokemons.controller;

import com.microservices.pokemons.dto.PokemonDto;
import com.microservices.pokemons.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/api/v1/pokemons")
@RestController
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public List<PokemonDto> getAllPokemons(){
        return pokemonService.getAllPokemons();
    }

    @PostMapping
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public PokemonDto insertOne(@RequestBody PokemonDto pokemonDto){
        return pokemonService.insertOne(pokemonDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public PokemonDto deleteOne(@PathVariable Long id){
        return pokemonService.deleteOne(id);
    }

    @GetMapping("{id}")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public PokemonDto getOneById(@PathVariable Long id){
        return pokemonService.getOneById(id);
    }
}
