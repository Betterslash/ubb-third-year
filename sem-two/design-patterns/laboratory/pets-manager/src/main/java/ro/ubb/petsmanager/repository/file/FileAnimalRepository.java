package ro.ubb.petsmanager.repository.file;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.petsmanager.model.Animal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@Setter
@Repository
public abstract class FileAnimalRepository<E extends Animal> implements CrudRepository<E, UUID> {


    protected String filePath;

    protected Gson converter;

    protected List<E> entities;

    protected abstract E fromFileFormatToEntity(String line);

    protected abstract String fromEntityToFileFormat(E entity);

    private void saveChanges(){
        try(var bufferedWriter = new BufferedWriter(new FileWriter(filePath))){
            entities.forEach(e -> {
                try {
                    bufferedWriter.write(fromEntityToFileFormat(e) + System.lineSeparator());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected FileAnimalRepository(Class<E> clazz) {
        filePath = "data/" + clazz.getSimpleName()+ ".txt";
        entities = new ArrayList<>();
        converter = new Gson();
        try(var bufferedReader = new BufferedReader(new FileReader(filePath))){
            entities = bufferedReader.lines()
                    .map(this::fromFileFormatToEntity)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <S extends E> S save(S entity) {
        if(entity.getId() != null){
            this.entities = this.entities.stream()
                   .filter(e -> e.getId() != entity.getId())
                    .collect(Collectors.toList());
            this.entities.add(entity);
            this.saveChanges();
            return entity;
        }
        entity.setId(UUID.randomUUID());
        this.entities.add(entity);
        return entity;
    }

    @Override
    public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
        var result = new ArrayList<S>();
        entities.forEach(e -> result.add(save(e)));
        this.saveChanges();
        return result;
    }

    @Override
    public Optional<E> findById(UUID uuid) {
        return this.entities.stream().filter(e -> e.getId() == uuid)
                .findFirst();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return this.entities.stream().anyMatch(e -> e.getId() == uuid);
    }

    @Override
    public List<E> findAll() {
        return this.entities;
    }

    @Override
    public List<E> findAllById(Iterable<UUID> uuids) {
        var uuidsList = StreamSupport.stream(uuids.spliterator(), false).toList();
        return this.entities.stream().filter(e -> uuidsList.contains(e.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return this.entities.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        this.entities = this.entities.stream().filter(e -> e.getId() != uuid).collect(Collectors.toList());
        this.saveChanges();
    }

    @Override
    public void delete(E entity) {
        this.entities = this.entities.stream().filter(e -> e.getId() != entity.getId()).collect(Collectors.toList());
        this.saveChanges();
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        uuids.forEach(this::deleteById);
        this.saveChanges();
    }

    @Override
    public void deleteAll(Iterable<? extends E> entities) {
        entities.forEach(this::delete);
        this.saveChanges();
    }

    @Override
    public void deleteAll() {
        this.entities = new ArrayList<>();
        this.saveChanges();
    }
}
