package ro.ubb.petsmanager.repository.file;

import org.springframework.stereotype.Repository;
import ro.ubb.petsmanager.model.Dog;

@Repository
public class FileDogRepository extends FileAnimalRepository<Dog> {
    protected FileDogRepository() {
        super(Dog.class);
    }

    @Override
    protected Dog fromFileFormatToEntity(String line) {
        return converter.fromJson(line, Dog.class);
    }

    @Override
    protected String fromEntityToFileFormat(Dog entity) {
        return converter.toJson(entity, Dog.class);
    }
}
