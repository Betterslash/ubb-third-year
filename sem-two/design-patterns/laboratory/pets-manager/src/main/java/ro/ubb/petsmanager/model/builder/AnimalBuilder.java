package ro.ubb.petsmanager.model.builder;

import lombok.Getter;
import lombok.Setter;
import ro.ubb.petsmanager.model.Animal;

import java.util.UUID;

@Getter
@Setter
public abstract class AnimalBuilder<E extends Animal> extends Builder<E> {
    protected String name;
    protected String race;
    protected Long age;
    protected boolean available;

    public void completeInformation(Animal result){
        result.setId(this.id);
        result.setAge(this.age);
        result.setAvailable(this.available);
        result.setRace(this.race);
        result.setName(this.name);
    }

    public AnimalBuilder<E> id(UUID id){
        this.id = id;
        return this;
    }

    public AnimalBuilder<E> name(String name) {
        this.name = name;
        return this;
    }

    public AnimalBuilder<E> race(String race) {
        this.race = race;
        return this;
    }

    public AnimalBuilder<E> age(Long age) {
        this.age = age;
        return this;
    }

    public AnimalBuilder<E> available(boolean available) {
        this.available = available;
        return this;
    }
}
