package symbol_table;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class SortedList implements SymbolTableRepresentation<Integer>{
    private final List<String> internalRepresentation;

    public SortedList(){
        internalRepresentation = new ArrayList<>();
    }

    public void add(String value){
        internalRepresentation.add(value);
        Collections.sort(internalRepresentation);
    }


    @Override
    public Integer getId(String value) {
        return internalRepresentation.indexOf(value);
    }

    @Override
    public Integer getNullEntry() {
        return -1;
    }
}

