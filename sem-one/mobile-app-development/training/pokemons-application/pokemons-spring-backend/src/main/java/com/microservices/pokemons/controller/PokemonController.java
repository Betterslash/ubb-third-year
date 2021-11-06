package com.microservices.pokemons.controller;

import com.microservices.pokemons.dto.ActionType;
import com.microservices.pokemons.dto.files.FileDto;
import com.microservices.pokemons.dto.notification.NotificationDto;
import com.microservices.pokemons.dto.pokemons.PokemonDto;
import com.microservices.pokemons.dto.pokemons.PokemonUserDto;
import com.microservices.pokemons.service.NotificationService;
import com.microservices.pokemons.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
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

    @GetMapping("/paginated/{pagine}")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public List<PokemonDto> getAllPokemonsPaginated(@RequestParam Long size, @PathVariable Long pagine){
        return pokemonService.getAllPaginated(size, pagine);
    }

    @PostMapping
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public PokemonDto insertOne(@RequestBody PokemonDto pokemonDto){
        var result = pokemonService.insertOne(pokemonDto);
        var notification = NotificationDto.Builder.build(ActionType.ADD, pokemonDto);
        notificationService.broadcast(notification);
        return result;
    }

    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public PokemonDto updateOne(@PathVariable Long id,
                                @RequestBody PokemonDto pokemonDto){
        var result = this.pokemonService.updateOne(id, pokemonDto);
        var notification = NotificationDto.Builder.build(ActionType.UPDATE, pokemonDto);
        notificationService.broadcast(notification);
        return result;
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_GYM_LEADER")
    public PokemonDto deleteOne(@PathVariable Long id){
        var result = pokemonService.deleteOne(id);
        var notification = NotificationDto.Builder.build(ActionType.DELETE, result);
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
        var result = this.pokemonService.releaseOne(id);
        notificationService.notifyUser(NotificationDto.Builder.build(ActionType.DELETE, result));
        return result;
    }

    @PostMapping("/catch")
    @RolesAllowed({"ROLE_PARTICIPANT", "ROLE_GYM_LEADER"})
    public PokemonDto catchPokemon(@RequestBody PokemonUserDto pokemonUserDto){
        return this.pokemonService.catchOne(pokemonUserDto);
    }

    @PostMapping("/synchronize")
    @RolesAllowed({"ROLE_GYM_LEADER"})
    public List<PokemonDto> insertOfflineCache(@RequestBody List<PokemonDto> cahcedPokemons){
        var response = this.pokemonService.insertMultiplePokemons(cahcedPokemons);
        var notifications = new ArrayList<NotificationDto>();
        cahcedPokemons.forEach(e -> {
            if(e.getDeletionMark() != null && e.getDeletionMark()){
                notifications.add(NotificationDto.Builder.build(ActionType.DELETE, e));
            }else{
                notifications.add(NotificationDto.Builder.build(ActionType.UPDATE, e));
            }
        });
        notificationService.multipleBrodacastToUsers(notifications);
        return response;
    }
}
