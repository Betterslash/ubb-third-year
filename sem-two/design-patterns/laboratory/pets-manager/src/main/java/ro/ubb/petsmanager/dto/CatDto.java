package ro.ubb.petsmanager.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CatDto extends AnimalDto{
    private String furColor;
    private String favouriteFood;
}
