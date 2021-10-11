import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class SortedList<E extends Comparable<E>> {
    private final List<E> internalRepresentation;

    public SortedList(){
        internalRepresentation = new ArrayList<>();
    }

    public int add(E value){
        internalRepresentation.add(value);
        Collections.sort(internalRepresentation);
        return internalRepresentation.indexOf(value);
    }
}

