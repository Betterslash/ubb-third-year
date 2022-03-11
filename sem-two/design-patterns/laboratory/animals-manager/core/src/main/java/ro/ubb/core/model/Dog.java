package ro.ubb.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity(name = "dogs")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Dog extends Animal {



}
