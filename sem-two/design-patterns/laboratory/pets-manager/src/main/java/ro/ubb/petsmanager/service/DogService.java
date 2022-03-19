package ro.ubb.petsmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.mapper.AnimalMapper;
import ro.ubb.petsmanager.model.Dog;
import ro.ubb.petsmanager.repository.file.FileAnimalRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DogService implements AnimalService<DogDto>{

    private final FileAnimalRepository<Dog> repository;

    private final AnimalMapper<Dog, DogDto> mapper;

    @Override
    public DogDto save(DogDto dto) {
        return mapper.fromEntityToDto(repository.save(mapper.fromDtoToEntity(dto)));
    }

    @Override
    public List<DogDto> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
