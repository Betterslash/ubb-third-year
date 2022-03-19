package ro.ubb.petsmanager.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.petsmanager.model.Animal;

import java.util.UUID;

public interface DbAnimalRepository<E extends Animal> extends JpaRepository<E, UUID>{
}
