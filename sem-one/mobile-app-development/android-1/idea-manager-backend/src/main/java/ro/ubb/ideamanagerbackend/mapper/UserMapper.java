package ro.ubb.ideamanagerbackend.mapper;

import org.mapstruct.Mapper;
import ro.ubb.ideamanagerbackend.dto.UserDto;
import ro.ubb.ideamanagerbackend.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto fromEntityToDto(UserEntity entity);
    UserEntity fromDtoToEntity(UserDto dto);
}
