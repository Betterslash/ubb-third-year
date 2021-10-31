package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Accesory extends Product{
    public Accesory() {
        super(Accesory.class.getName(), 20);
    }
}
