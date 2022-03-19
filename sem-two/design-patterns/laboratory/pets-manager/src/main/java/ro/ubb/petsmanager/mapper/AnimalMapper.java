package ro.ubb.petsmanager.mapper;

import ro.ubb.petsmanager.dto.AnimalDto;
import ro.ubb.petsmanager.model.Animal;

public interface AnimalMapper<E extends Animal, D extends AnimalDto> {
    D fromEntityToDto(E entity);
    E fromDtoToEntity(D dto);
}
