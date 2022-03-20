package ro.ubb.petsmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.petsmanager.dto.LoginDto;
import ro.ubb.petsmanager.dto.UserDto;
import ro.ubb.petsmanager.service.UserService;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController extends ApiController{

    private final UserService service;

    @PostMapping("/login")
    public UserResponse<Object> login(@RequestBody LoginDto userDto){
        return UserResponse.builder()
                .response(service.login(userDto))
                .build();
    }

    @PostMapping("/register")
    public UserResponse<Object> register(@RequestBody UserDto userDto){
        return UserResponse.builder()
                .response(service.register(userDto))
                .build();
    }
}
