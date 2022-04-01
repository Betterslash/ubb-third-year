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

public interface AnimalService<D extends AnimalDto> {
    D save(D save);
    List<D> getAll();
    List<D> getOwned(String username);
    D adopt(AdoptRequest toBeAdoptedId);
}

