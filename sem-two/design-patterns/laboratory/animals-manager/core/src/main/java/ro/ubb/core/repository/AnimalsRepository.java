package ro.ubb.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.core.model.Animal;

import java.util.UUID;

public interface AnimalsRepository<E extends Animal> extends JpaRepository<E, UUID> {
}
