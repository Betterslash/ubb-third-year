package ro.ubb.ideamanagerbackend.service;

import org.springframework.security.core.Authentication;
import ro.ubb.ideamanagerbackend.dto.UserDto;
import ro.ubb.ideamanagerbackend.payload.RegisterRequest;

public interface AuthService {
    String login(Authentication authentication);
    UserDto register(RegisterRequest request);
}
