package com.microservices.pokemons.payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private LocalDate birthday;
}
