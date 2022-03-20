package ro.ubb.petsmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.dto.LoginDto;
import ro.ubb.petsmanager.dto.UserDto;
import ro.ubb.petsmanager.mapper.UserMapper;
import ro.ubb.petsmanager.repository.postgres.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public boolean login(LoginDto loginRequest) {
        return repository.findAll().stream()
                .anyMatch(e -> Objects.equals(e.getUsername(), loginRequest.getUsername()) && Objects.equals(e.getPassword(), loginRequest.getPassword()));
    }

    @Override
    public boolean register(UserDto registerRequest) {
        var entity = mapper.fromdDtoToEntity(registerRequest);
        var alreadyRegistered = repository.findAll()
                .stream().anyMatch(e -> Objects.equals(e.getUsername(), entity.getUsername()));
        if(alreadyRegistered){
            return false;
        }else{
            repository.save(entity);
            return true;
        }
    }
}
