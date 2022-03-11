package ro.ubb.core.repository;

import ro.ubb.core.model.Cat;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends AnimalsRepository<Cat>{
}
