package com.microservices.pokemons.model.enums;

public enum TrainerPermission {
    SEE_POKEMONS("read:pokemons"),
    MODIFY_POKEMONS("write:pokemons");
    private final String permissionDescription;

    TrainerPermission(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }
}
