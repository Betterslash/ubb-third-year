package com.microservices.pokemons.service;

import com.microservices.pokemons.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonTrainerDetailsServiceImpl implements PokemonTrainerDetailsService {

    private final TrainerRepository trainerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return trainerRepository.findByUsername(username);
    }


}
