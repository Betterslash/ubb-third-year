package ro.ubb.ideamanagerbackend.mapper;

import org.mapstruct.Mapper;
import ro.ubb.ideamanagerbackend.dto.IdeaDto;
import ro.ubb.ideamanagerbackend.model.IdeaEntity;
import ro.ubb.ideamanagerbackend.model.UserEntity;

@Mapper(componentModel = "spring")
public interface IdeaMapper {

    default Long fromEntityToDtoId(UserEntity value){
        return value.getId();
    }

    default IdeaEntity fromDtoToEntity(IdeaDto dto, UserEntity user){
        if (dto == null) {
            return null;
        } else {
            var ideaEntity = new IdeaEntity();
            ideaEntity.setId(dto.getId());
            ideaEntity.setTitle(dto.getTitle());
            ideaEntity.setText(dto.getText());
            ideaEntity.setNeededBudget(dto.getNeededBudget());
            ideaEntity.setCurrentBudget(dto.getCurrentBudget());
            ideaEntity.setRating(dto.getRating());
            ideaEntity.setUser(user);
            ideaEntity.setDomain(dto.getDomain());
            ideaEntity.setLocalDate(dto.getLocalDate());
            ideaEntity.setRateCount(dto.getRateCount());
            return ideaEntity;
        }
    }

    IdeaDto fromEntityToDto(IdeaEntity entity);
}
