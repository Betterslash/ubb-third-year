package com.microservices.pokemons.service;

import com.microservices.pokemons.payload.LoginRequest;
import com.microservices.pokemons.model.user.PokemonTrainer;
import com.microservices.pokemons.model.user.TrainerRole;
import com.microservices.pokemons.payload.RegisterRequest;
import com.microservices.pokemons.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final TrainerRepository repository;

    @Override
    public String login(LoginRequest request) {
        var user = repository.findByUsername(request.username);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public PokemonTrainer register(RegisterRequest user) {
        var trainer = PokemonTrainer.builder()
                        .password(passwordEncoder.encode(user.getPassword()))
                                .username(user.getUsername())
                                        .role(TrainerRole.PARTICIPANT)
                                                .build();
        return repository.save(trainer);
    }
}
