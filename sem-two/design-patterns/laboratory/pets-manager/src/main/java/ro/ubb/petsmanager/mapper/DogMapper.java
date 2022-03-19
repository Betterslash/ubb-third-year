package ro.ubb.petsmanager.mapper;

import org.springframework.stereotype.Component;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.model.Dog;

import java.util.UUID;

@Component
public class DogMapper implements AnimalMapper<Dog, DogDto>{

    @Override
    public DogDto fromEntityToDto(Dog entity) {
        return DogDto.builder()
                .id(entity.getId().toString())
                .available(entity.isAvailable())
                .age(entity.getAge())
                .name(entity.getName())
                .furColor(entity.getFurColor())
                .race(entity.getRace())
                .weight(entity.getWeight())
                .height(entity.getHeight())
                .build();
    }

    @Override
    public Dog fromDtoToEntity(DogDto dto) {
        var id = UUID.randomUUID();
        if(dto.getId() != null){
            id = UUID.fromString(dto.getId());
        }
        return Dog.builder
                .furColor(dto.getFurColor())
                .weight(dto.getWeight())
                .height(dto.getHeight())
                .id(id)
                .available(dto.isAvailable())
                .age(dto.getAge())
                .name(dto.getName())
                .race(dto.getRace())
                .build();
    }
}

