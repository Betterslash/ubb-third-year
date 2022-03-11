package ro.ubb.core.repository;

import ro.ubb.core.model.Dog;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends AnimalsRepository<Dog>{
}
