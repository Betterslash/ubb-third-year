package ro.ubb.petsmanager.service;

import ro.ubb.petsmanager.dto.AdoptRequest;
import ro.ubb.petsmanager.dto.AnimalDto;

import java.util.List;

public interface AnimalService<D extends AnimalDto> {
    D save(D save);
    List<D> getAll();
    List<D> getOwned(String username);
    D adopt(AdoptRequest toBeAdoptedId);
    D getById(String id);
}

