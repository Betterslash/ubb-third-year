package com.microservices.pokemons.service;

import com.microservices.pokemons.payload.LoginRequest;
import com.microservices.pokemons.model.TrainerEntity;
import com.microservices.pokemons.payload.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);
    TrainerEntity register(RegisterRequest request);
}
