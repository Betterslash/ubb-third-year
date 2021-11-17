package ro.ubb.ideamanagerbackend.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleDescription;

    public Set<? extends GrantedAuthority> getGrantedAuthorities(){
        var result =  new HashSet<GrantedAuthority>();
        result.add(new SimpleGrantedAuthority("ROLE_" + this.roleDescription));
        return result;
    }
}
