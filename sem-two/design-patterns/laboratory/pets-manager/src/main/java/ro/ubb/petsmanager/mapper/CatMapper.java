package ro.ubb.petsmanager.mapper;

import org.springframework.stereotype.Component;
import ro.ubb.petsmanager.dto.AnimalDto;
import ro.ubb.petsmanager.dto.CatDto;
import ro.ubb.petsmanager.model.Animal;
import ro.ubb.petsmanager.model.Cat;

import java.util.UUID;

@Component
public class CatMapper implements AnimalMapper<Cat, CatDto> {

    @Override
    public CatDto fromEntityToDto(Cat entity) {
        return CatDto.builder()
                .id(entity.getId().toString())
                .age(entity.getAge())
                .race(entity.getRace())
                .name(entity.getName())
                .available(entity.isAvailable())
                .favouriteFood(entity.getFavouriteFood())
                .furColor(entity.getFurColor())
                .build();
    }

    @Override
    public Cat fromDtoToEntity(CatDto dto) {
        var id = UUID.randomUUID();
        if(dto.getId() != null){
            id = UUID.fromString(dto.getId());
        }
        return Cat.builder
                .favouriteFood(dto.getFavouriteFood())
                .furColor(dto.getFurColor())
                .id(id)
                .age(dto.getAge())
                .race(dto.getRace())
                .name(dto.getName())
                .available(dto.isAvailable())
                .build();
    }
}
