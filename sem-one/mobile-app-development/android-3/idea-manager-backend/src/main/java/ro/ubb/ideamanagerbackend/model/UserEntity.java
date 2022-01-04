package ro.ubb.ideamanagerbackend.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.ubb.ideamanagerbackend.shared.UserRole;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "app_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDate birthday;

    private UserRole role;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user",
            targetEntity = IdeaEntity.class)
    private List<IdeaEntity> ideas;

    @Transient
    private boolean isAccountNonExpired = true;

    @Transient
    private boolean isAccountNonLocked = true;

    @Transient
    private boolean isCredentialsNonExpired = true;

    @Transient
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getGrantedAuthorities();
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
}
