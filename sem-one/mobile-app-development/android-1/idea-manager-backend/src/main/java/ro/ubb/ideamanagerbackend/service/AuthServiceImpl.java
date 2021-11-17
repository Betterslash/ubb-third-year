package ro.ubb.ideamanagerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.ubb.ideamanagerbackend.dto.UserDto;
import ro.ubb.ideamanagerbackend.mapper.UserMapper;
import ro.ubb.ideamanagerbackend.payload.LoginRequest;
import ro.ubb.ideamanagerbackend.payload.RegisterRequest;
import ro.ubb.ideamanagerbackend.provider.JwtTokenProvider;
import ro.ubb.ideamanagerbackend.repository.UserRepository;
import ro.ubb.ideamanagerbackend.shared.UserRole;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public String login(LoginRequest request) {
        var user = repository.findByUsername(request.username);
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public UserDto register(RegisterRequest user) {
        var trainer = UserDto.builder()
                .password(passwordEncoder.encode(user.getPassword()))
                .username(user.getUsername())
                .birthday(LocalDate.now())
                .email(user.getEmail())
                .role(UserRole.USER)
                .build();
        return mapper.fromEntityToDto(
                repository.save(
                        mapper.fromDtoToEntity(trainer)));
    }
}
