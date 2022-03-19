package ro.ubb.petsmanager.repository.file;

import org.springframework.stereotype.Repository;
import ro.ubb.petsmanager.model.Cat;

@Repository
public class FileCatRepository extends FileAnimalRepository<Cat>{
    protected FileCatRepository() {
        super(Cat.class);
    }

    @Override
    protected Cat fromFileFormatToEntity(String line) {
        return converter.fromJson(line, Cat.class);
    }

    @Override
    protected String fromEntityToFileFormat(Cat entity) {
        return converter.toJson(entity, Cat.class);
    }
}
