package ro.ubb.petsmanager.service;

import ro.ubb.petsmanager.dto.LoginDto;
import ro.ubb.petsmanager.dto.UserDto;

public interface UserService{
    boolean login(LoginDto loginRequest);
    boolean register(UserDto registerRequest);
}
