package ro.ubb.petsmanager.mapper;

import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.dto.UserDto;
import ro.ubb.petsmanager.model.User;

@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public User fromdDtoToEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build();
    }

    @Override
    public UserDto fromEntityToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
