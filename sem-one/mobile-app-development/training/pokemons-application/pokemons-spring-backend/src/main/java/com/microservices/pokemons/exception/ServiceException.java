package com.microservices.pokemons.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
