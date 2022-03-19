package ro.ubb.petsmanager.repository.postgres;

import org.springframework.stereotype.Repository;
import ro.ubb.petsmanager.model.Dog;

@Repository
public interface DbDogRepository extends DbAnimalRepository<Dog> {
}
