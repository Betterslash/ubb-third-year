package ro.ubb.petsmanager.service;

import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.mapper.AnimalMapper;
import ro.ubb.petsmanager.model.Dog;
import ro.ubb.petsmanager.repository.postgres.DbAnimalRepository;

@Service
public class DogService extends AbstractAnimalService<DogDto, Dog> {

    public DogService(DbAnimalRepository<Dog> repository, AnimalMapper<Dog, DogDto> mapper) {
        super(repository, mapper);
    }
}
