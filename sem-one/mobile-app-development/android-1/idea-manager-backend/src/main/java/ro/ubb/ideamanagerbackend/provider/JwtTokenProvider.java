package ro.ubb.ideamanagerbackend.provider;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    String generateToken(Authentication authentication);
    Claims getClaimsFromJWT(String token);
    boolean validateToken(String authToken);
}
