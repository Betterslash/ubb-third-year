import lombok.Data;
import symbol_table.SymbolTableRepresentation;


@Data
public class SymbolTable<K, R extends SymbolTableRepresentation<K>> {
    public final R internalRepresentation;

    public SymbolTable(R internalRepresentation) {
        this.internalRepresentation = internalRepresentation;
    }


    public void add(String value){
        internalRepresentation.add(value);
    }

    public K getId(String value){
        return this.internalRepresentation.getId(value);
    }

    public K getNullValue(){
        return this.internalRepresentation.getNullEntry();
    }
}
