import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class SymbolTable {
    public final List<String> internalRepresentation;

    public SymbolTable() {
        this.internalRepresentation = new ArrayList<>();
    }

    public void add(String value){
        if(!internalRepresentation.contains(value)){
            this.internalRepresentation.add(value);
            Collections.sort(internalRepresentation);
        }
    }

    public Integer getId(String value){
        return this.internalRepresentation.indexOf(value);
    }
}
