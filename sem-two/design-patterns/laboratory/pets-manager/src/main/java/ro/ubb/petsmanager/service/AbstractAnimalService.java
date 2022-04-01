package ro.ubb.petsmanager.service;

import lombok.RequiredArgsConstructor;
import ro.ubb.petsmanager.dto.AdoptRequest;
import ro.ubb.petsmanager.dto.AnimalDto;
import ro.ubb.petsmanager.mapper.AnimalMapper;
import ro.ubb.petsmanager.model.Animal;
import ro.ubb.petsmanager.repository.postgres.DbAnimalRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractAnimalService<D extends AnimalDto, E extends Animal> implements AnimalService<D> {

    private final DbAnimalRepository<E> repository;

    private final AnimalMapper<E, D> mapper;

    @Override
    public D save(D save) {
        var entity = mapper.fromDtoToEntity(save);
        var result = repository.save(entity);
        return mapper.fromEntityToDto(result);
    }

    @Override
    public List<D> getAll() {
        return this.repository.findAll()
                .stream().map(mapper::fromEntityToDto)
                .toList();
    }

    @Override
    public List<D> getOwned(String username) {
        return this.repository.findAll()
                .stream()
                .map(mapper::fromEntityToDto)
                .filter(e -> Objects.equals(e.getOwner(), username))
                .toList();
    }

    @Override
    public D adopt(AdoptRequest toBeAdoptedId) {
        var entity = repository.findById(UUID.fromString(toBeAdoptedId.getAnimalId()))
                .orElseThrow(RuntimeException::new);
        entity.setOwner(toBeAdoptedId.getUsername());
        entity.setAvailable(false);
        return this.save(mapper.fromEntityToDto(entity));
    }
}
