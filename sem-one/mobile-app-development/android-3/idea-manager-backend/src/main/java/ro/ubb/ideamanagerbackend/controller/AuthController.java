package ro.ubb.ideamanagerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.ideamanagerbackend.payload.LoginRequest;
import ro.ubb.ideamanagerbackend.payload.RegisterRequest;
import ro.ubb.ideamanagerbackend.payload.TokenHolder;
import ro.ubb.ideamanagerbackend.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public TokenHolder authenticateUser(@RequestBody LoginRequest loginRequest) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String token = userService.login(authentication);
        return TokenHolder
                .builder()
                .token(token)
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request){
        var trainer = userService.register(request);
        return ResponseEntity.ok(trainer);
    }

}
