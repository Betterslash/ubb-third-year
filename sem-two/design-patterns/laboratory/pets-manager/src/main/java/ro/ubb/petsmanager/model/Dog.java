package ro.ubb.petsmanager.model;

import lombok.*;
import ro.ubb.petsmanager.model.builder.DogBuilder;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Dog extends Animal{
    private String furColor;
    private Long weight;
    private Long height;

    @Transient
    public static DogBuilder builder = new DogBuilder();
}
