package com.microservices.pokemons.controller;

import com.microservices.pokemons.dto.NotificationDto;
import com.microservices.pokemons.dto.PokemonDto;
import com.microservices.pokemons.service.NotificationService;
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
    private final NotificationService notificationService;

    @GetMapping
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public List<PokemonDto> getAllPokemons(){
        return pokemonService.getAllPokemons();
    }

    @PostMapping
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public PokemonDto insertOne(@RequestBody PokemonDto pokemonDto){
        var result = pokemonService.insertOne(pokemonDto);
        var notification = new NotificationDto("ADD", String.format("Added %s", result.getName()));
        notificationService.broadcast(notification);
        return result;
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public PokemonDto updateOne(@PathVariable Long id, @RequestBody PokemonDto pokemonDto){
        var result = this.pokemonService.updateOne(id, pokemonDto);
        var notification = new NotificationDto("UPDATE", String.format("Updated %s ", pokemonDto.getName()));
        notificationService.broadcast(notification);
        return result;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_GYM_LEADER")
    public PokemonDto deleteOne(@PathVariable Long id){
        var result = pokemonService.deleteOne(id);
        var notification = new NotificationDto("DELETE", String.format("Deleted %s", result.getName()));
        notificationService.broadcast(notification);
        return result;
    }

    @GetMapping("{id}")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public PokemonDto getOneById(@PathVariable Long id){
        return pokemonService.getOneById(id);
    }

    @GetMapping("/caught")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public List<PokemonDto> getCaughtPokemons(){
        return this.pokemonService.getUserPokemons();
    }

    @DeleteMapping("/release/{id}")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public PokemonDto releasePokemon(@PathVariable Long id){
        return this.pokemonService.releaseOne(id);
    }

    @PostMapping("/catch/{id}")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public PokemonDto catchPokemon(@PathVariable Long id){
        return this.pokemonService.catchOne(id);
    }
}
