package ro.ubb.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity(name = "rabbits")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Rabbit extends Animal{
}
