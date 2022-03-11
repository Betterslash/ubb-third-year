package ro.ubb.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity(name = "cats")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Cat extends Animal{
}
