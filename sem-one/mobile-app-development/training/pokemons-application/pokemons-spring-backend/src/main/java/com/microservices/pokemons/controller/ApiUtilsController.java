package com.microservices.pokemons.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/utils")
public class ApiUtilsController {
    @GetMapping
    public String ping(){
        return "UP";
    }
}
