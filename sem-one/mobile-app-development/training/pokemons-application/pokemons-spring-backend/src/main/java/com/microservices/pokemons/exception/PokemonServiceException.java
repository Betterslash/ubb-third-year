package com.microservices.pokemons.exception;

public class PokemonServiceException extends ServiceException {
    public PokemonServiceException(String message){
        super(message);
    }
}
