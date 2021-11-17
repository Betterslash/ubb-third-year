package ro.ubb.ideamanagerbackend.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Override
    public String generateToken(Authentication authentication) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 86400 * 1000L))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, "jwtConfig.getSecret()".getBytes())
                .compact();
    }

    @Override
    public Claims getClaimsFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey("jwtConfig.getSecret()".getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean validateToken(String authToken) {
        Jwts.parser()
                .setSigningKey("jwtConfig.getSecret()".getBytes())
                .parseClaimsJws(authToken);
        return true;
    }
}
