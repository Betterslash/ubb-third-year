package ro.ubb.petsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.ubb.petsmanager.model.builder.CatBuilder;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cat extends Animal{
    private String furColor;
    private String favouriteFood;

    @Transient
    public static CatBuilder builder = new CatBuilder();
}
