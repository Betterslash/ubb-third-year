package com.microservices.pokemons.model.pokemons;

import com.microservices.pokemons.model.enums.TrainerRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "trainer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TrainerEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;

    private String username;
    private String password;
    private String email;
    private LocalDate birthday;
    private TrainerRole role;

    @OneToMany(mappedBy = "trainer",
            targetEntity = PokemonUserEntity.class,
            cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PokemonEntity> pokemons;

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
