package com.microservices.pokemons.model.user;

import com.microservices.pokemons.model.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PokemonTrainer extends BaseEntity implements UserDetails {

    private String username;
    private String password;
    private TrainerRole role;


    @Transient
    private boolean isAccountNonExpired;

    @Transient
    private boolean isAccountNonLocked;

    @Transient
    private boolean isCredentialsNonExpired;

    @Transient
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getGrantedAuthorities();
    }

}
