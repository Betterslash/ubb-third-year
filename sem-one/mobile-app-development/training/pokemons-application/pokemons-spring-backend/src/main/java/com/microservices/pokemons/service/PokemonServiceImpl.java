package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.PokemonDto;
import com.microservices.pokemons.exception.PokemonServiceException;
import com.microservices.pokemons.mapper.PokemonMapper;
import com.microservices.pokemons.model.PokemonTypeEntity;
import com.microservices.pokemons.model.PokemonUserEntity;
import com.microservices.pokemons.model.TrainerEntity;
import com.microservices.pokemons.model.embeddables.PokemonUserKey;
import com.microservices.pokemons.repository.PokemonRepository;
import com.microservices.pokemons.repository.PokemonTypesRepository;
import com.microservices.pokemons.repository.PokemonUserRepository;
import com.microservices.pokemons.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final PokemonMapper pokemonMapper;
    private final PokemonRepository pokemonRepository;
    private final TrainerRepository trainerRepository;
    private final PokemonTypesRepository pokemonTypesRepository;
    private final PokemonUserRepository pokemonUserRepository;

    private Long getUserId(){
        var authenticatedUser = (TrainerEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var currentUsername = authenticatedUser.getUsername();
        return trainerRepository.findByUsername(currentUsername).getTrainerId();
    }

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

    @Override
    public List<PokemonDto> getUserPokemons(){
        return pokemonUserRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getTrainer().getTrainerId(), getUserId()))
                .map(t -> pokemonRepository.getById(t.getPokemon().getPokemonId()))
                .map(pokemonMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PokemonDto releaseOne(Long id) {
        var response = this.pokemonMapper.fromEntityToDto(this.pokemonRepository.getById(id));
        var entryId = new PokemonUserKey(getUserId(), id);
        this.pokemonUserRepository.deleteById(entryId);
        return response;
    }

    @Override
    public PokemonDto catchOne(Long id) {
        var caughtPokemon = this.pokemonRepository.getById(id);
        var userProfile = this.trainerRepository.getById(getUserId());
        var pokemonUserKey = new PokemonUserKey(userProfile.getTrainerId(), caughtPokemon.getPokemonId());
        var userPokemonEntry = PokemonUserEntity.builder()
                .id(pokemonUserKey)
                .pokemon(caughtPokemon)
                .trainer(userProfile)
                .build();
        return this.pokemonMapper
                .fromEntityToDto(this.pokemonUserRepository
                .save(userPokemonEntry)
                .getPokemon());
    }

    @Override
    public PokemonDto updateOne(Long id, PokemonDto pokemonDto) {
        var found = this.pokemonRepository.findById(id);
        var result = new AtomicReference<>(new PokemonDto());
        found.ifPresentOrElse(e -> {
            e.setName(pokemonDto.getName());
            e.setTypes(this.pokemonTypesRepository.findByTypeOneAndTypeTwo(pokemonDto.getTypes().getTypeOne(), pokemonDto.getTypes().getTypeTwo())
                    .orElseGet(() -> pokemonTypesRepository.save(new PokemonTypeEntity(null,e.getTypes().getTypeOne(), e.getTypes().getTypeTwo() ,null))));
                    result.set(this.pokemonMapper
                            .fromEntityToDto(this.pokemonRepository
                                    .save(e)));
                },
                () -> result.set(this.insertOne(pokemonDto)));
        return result.get();
    }
}
