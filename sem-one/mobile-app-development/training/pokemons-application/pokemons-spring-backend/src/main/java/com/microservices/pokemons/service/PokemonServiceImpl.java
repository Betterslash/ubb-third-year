package com.microservices.pokemons.service;

import com.microservices.pokemons.dto.PokemonDto;
import com.microservices.pokemons.dto.PokemonUserDto;
import com.microservices.pokemons.exception.PokemonServiceException;
import com.microservices.pokemons.mapper.PokemonMapper;
import com.microservices.pokemons.model.PokemonTypeEntity;
import com.microservices.pokemons.model.PokemonUserEntity;
import com.microservices.pokemons.model.TrainerEntity;
import com.microservices.pokemons.model.embeddables.PokemonUserKey;
import com.microservices.pokemons.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final PokemonPagingAndSortingRepository pokemonPagingAndSortingRepository;

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
        var possibleType = getType(pokemonDto);
        var toBeSaved = this.pokemonMapper.fromDtoToEntity(pokemonDto, this.pokemonRepository, this.pokemonTypesRepository);
        toBeSaved.setTypes(possibleType);
        return this.pokemonMapper.fromEntityToDto(this.pokemonRepository.save(toBeSaved));
    }

    @Override
    public PokemonDto deleteOne(Long id) {
        var pokemon = this.pokemonRepository.findById(id);
        try {
            pokemon.ifPresent(e -> {
                var evolution = this.pokemonRepository.findByEvolvesFrom(e);
                evolution.ifPresent(t -> {
                    var secondEvolution = this.pokemonRepository.findByEvolvesFrom(t);
                    secondEvolution.ifPresent(z -> {
                        z.getEvolvesFrom().setEvolvesFrom(null);
                        this.pokemonRepository.save(z);
                        z.setEvolvesFrom(null);
                        this.pokemonRepository.save(z);
                        this.pokemonRepository.deleteById(z.getPokemonId());
                    });
                    t.setEvolvesFrom(null);
                    this.pokemonRepository.save(t);
                    this.pokemonRepository.deleteById(t.getPokemonId());});
                e.setEvolvesFrom(null);
                this.pokemonRepository.save(e);
                this.pokemonRepository.deleteById(e.getPokemonId());});
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
    public PokemonDto catchOne(PokemonUserDto pokemonUserDto) {
        var caughtPokemon = this.pokemonRepository.getById(pokemonUserDto.getPokemonId());
        var userProfile = this.trainerRepository.getById(getUserId());
        var pokemonUserKey = new PokemonUserKey(userProfile.getTrainerId(), caughtPokemon.getPokemonId());
        var result = new AtomicReference<>(new PokemonDto());
        this.pokemonUserRepository.findById(pokemonUserKey)
                .ifPresentOrElse(e -> {
                    e.setPokemonName(pokemonUserDto.getPokemonName());
                    e.setCaughtNumber(e.getCaughtNumber() + 1);
                    e.setLastCaught(LocalDate.now());
                    result.set(this.pokemonMapper
                                    .fromEntityToDto(this.pokemonUserRepository
                                            .save(e)
                                            .getPokemon()));
                },
                        () -> {var userPokemonEntry = PokemonUserEntity.builder()
                                .id(pokemonUserKey)
                                .pokemon(caughtPokemon)
                                .trainer(userProfile)
                                .pokemonName("")
                                .caughtNumber(1L)
                                .lastCaught(LocalDate.now())
                                .build();

                            result.set(this.pokemonMapper
                                    .fromEntityToDto(this.pokemonUserRepository
                                            .save(userPokemonEntry)
                                            .getPokemon()));});
        return result.get();

    }

    @Override
    public List<PokemonDto> getAllPaginated(Long size, Long from) {
        var page = Integer.parseInt(String.valueOf(from));
        var pagebable = PageRequest.of(page, Integer.parseInt(String.valueOf(size)));
        return this.pokemonPagingAndSortingRepository.findAll(pagebable)
                .map(pokemonMapper::fromEntityToDto)
                .toList();
    }

    private PokemonTypeEntity getType(PokemonDto pokemonDto){
        var possibleTypes = this.pokemonTypesRepository.findAll()
                .stream().filter(g -> g.getTypeOne() == pokemonDto.getTypes().getTypeOne() && g.getTypeTwo() == pokemonDto.getTypes().getTypeTwo())
                .collect(Collectors.toList());
        var possibleType = new PokemonTypeEntity();
        if(possibleTypes.size() > 0){
            possibleType = possibleTypes.get(0);
        }else{
            possibleType = pokemonTypesRepository.save(new PokemonTypeEntity(null,pokemonDto.getTypes().getTypeOne(), pokemonDto.getTypes().getTypeTwo() ,null));
        }
        return possibleType;
    }

    @Override
    public PokemonDto updateOne(Long id, PokemonDto pokemonDto) {
        var possibleType = getType(pokemonDto);
        var found = this.pokemonRepository.findById(id);
        var result = new AtomicReference<>(new PokemonDto());
        found.ifPresentOrElse(e -> {
            e.setName(pokemonDto.getName());
            e.setHasShiny(pokemonDto.isHasShiny());
            e.setRegisteredAt(LocalDate.parse(pokemonDto.getRegisteredAt()));
            e.setCatchRate(pokemonDto.getCatchRate());
            e.setTypes(possibleType);
                    result.set(this.pokemonMapper
                            .fromEntityToDto(this.pokemonRepository
                                    .save(e)));
                },
                () -> {
                    pokemonDto.id = null;
                    pokemonDto.setEvolvesFrom(null);
                    result.set(this.insertOne(pokemonDto));
                });
        return result.get();
    }

    @Override
    @Transactional
    public List<PokemonDto> insertMultiplePokemons(List<PokemonDto> pokemons) {
        var results = new ArrayList<PokemonDto>();
        pokemons.forEach(e -> {
            if(e.getDeletionMark() != null && e.getDeletionMark()){
                this.pokemonRepository.findById(e.id).ifPresent(q -> this.pokemonRepository.deleteById(q.getPokemonId()));
            }else if(e.getDeletionMark() == null || !e.getDeletionMark()){
                results.add(this.updateOne(e.id, e));
            }
        });
        return results;
    }

}
