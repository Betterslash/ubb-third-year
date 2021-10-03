package com.microservices.pokemons.model.enums;

import com.microservices.pokemons.model.enums.TrainerPermission;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum TrainerRole {
    GYM_LEADER("GYM_LEADER"),
    PARTICIPANT("PARTICIPANT");

    private final String roleDescription;
    private final Set<TrainerPermission> permissions;

    private Set<TrainerPermission> buildPermissions(String roleDescription){
        var permissions = new HashSet<TrainerPermission>();
        switch (roleDescription){
            case "GYM_LEADER" -> {
                permissions.add(TrainerPermission.SEE_POKEMONS);
                permissions.add(TrainerPermission.MODIFY_POKEMONS);
                return permissions;
            }
            case "PARTICIPANT" ->{
                permissions.add(TrainerPermission.SEE_POKEMONS);
                return permissions;
            }
            default -> throw new IllegalStateException("Unexpected value: " + roleDescription);
        }
    }

    public Set<? extends GrantedAuthority> getGrantedAuthorities(){
        var result = this.permissions.stream()
                .map(e -> new SimpleGrantedAuthority(this.roleDescription))
                .collect(Collectors.toSet());
        result.add(new SimpleGrantedAuthority("ROLE_"+this.roleDescription));
        return result;
    }

    TrainerRole(String roleDescription){
        this.roleDescription = roleDescription;
        permissions = buildPermissions(roleDescription);
    }
}
