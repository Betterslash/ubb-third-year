package Pif;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProgramInternalForm<K> {
    private final List<K> internalRepresentaion;

    public ProgramInternalForm() {
        this.internalRepresentaion = new ArrayList<>();
    }

    public void add(K pair){
        internalRepresentaion.add(pair);
    }

}
