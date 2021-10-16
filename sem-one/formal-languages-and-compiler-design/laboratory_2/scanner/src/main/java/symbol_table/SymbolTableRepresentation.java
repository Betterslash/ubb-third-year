package symbol_table;

public interface SymbolTableRepresentation<K> {
    void add(String value);
    K getId(String value);
    K getNullEntry();
}
