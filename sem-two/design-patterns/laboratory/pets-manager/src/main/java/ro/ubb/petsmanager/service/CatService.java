package ro.ubb.petsmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.dto.CatDto;
import ro.ubb.petsmanager.mapper.AnimalMapper;
import ro.ubb.petsmanager.model.Cat;
import ro.ubb.petsmanager.repository.file.FileAnimalRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CatService implements AnimalService<CatDto> {

    private final FileAnimalRepository<Cat> repository;

    private final AnimalMapper<Cat, CatDto> mapper;

    @Override
    public CatDto save(CatDto dto) {
        return mapper.fromEntityToDto(repository.save(mapper.fromDtoToEntity(dto)));
    }

    @Override
    public List<CatDto> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
