package ro.ubb.petsmanager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AnimalDto extends BaseDto {
    protected String name;
    protected String race;
    protected Long age;
    protected boolean available;
    protected String owner;
}
