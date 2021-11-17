package ro.ubb.ideamanagerbackend.service;

import ro.ubb.ideamanagerbackend.dto.UserDto;
import ro.ubb.ideamanagerbackend.payload.LoginRequest;
import ro.ubb.ideamanagerbackend.payload.RegisterRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
    UserDto register(RegisterRequest request);
}
