package com.microservices.pokemons.service;

import com.microservices.pokemons.payload.LoginRequest;
import com.microservices.pokemons.model.user.PokemonTrainer;
import com.microservices.pokemons.payload.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);
    PokemonTrainer register(RegisterRequest request);
}
