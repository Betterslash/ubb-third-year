package ro.ubb.petsmanager.mapper;

import ro.ubb.petsmanager.dto.UserDto;
import ro.ubb.petsmanager.model.User;

public interface UserMapper {
    User fromdDtoToEntity(UserDto userDto);
    UserDto fromEntityToDto(User user);
}
