package ro.ubb.petsmanager.service;

import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.dto.CatDto;
import ro.ubb.petsmanager.mapper.AnimalMapper;
import ro.ubb.petsmanager.model.Cat;
import ro.ubb.petsmanager.repository.postgres.DbAnimalRepository;

@Service
public class CatService extends AbstractAnimalService<CatDto, Cat> {

    public CatService(DbAnimalRepository<Cat> repository, AnimalMapper<Cat, CatDto> mapper) {
        super(repository, mapper);
    }
}
