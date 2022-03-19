package ro.ubb.petsmanager.repository.postgres;

import org.springframework.stereotype.Repository;
import ro.ubb.petsmanager.model.Cat;

@Repository
public interface DbCatRepository extends DbAnimalRepository<Cat> {
}
