package ro.ubb.ideamanagerbackend.mapper;

import org.mapstruct.Mapper;
import ro.ubb.ideamanagerbackend.dto.IdeaDto;
import ro.ubb.ideamanagerbackend.model.IdeaEntity;

@Mapper(componentModel = "spring")
public interface IdeaMapper {
    IdeaEntity fromDtoToEntity(IdeaDto dto);
    IdeaDto fromEntityToDto(IdeaEntity entity);
}
