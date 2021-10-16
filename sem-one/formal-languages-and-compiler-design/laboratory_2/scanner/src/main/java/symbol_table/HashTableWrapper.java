package symbol_table;

import Pif.CustomPair;
import lombok.Data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class HashTableWrapper implements SymbolTableRepresentation<CustomPair>{
    private final Hashtable<Integer, ArrayList<String>> internalRepresentation;
    private static final int divisonFactor = 31;

    public HashTableWrapper(){
        internalRepresentation = new Hashtable<>();
    }

    private int computeHashFunction(int valueOfToken){
        return valueOfToken % divisonFactor + valueOfToken;
    }

    public void add(String token) {
        var tokenAsCharArray = token.toCharArray();
        var valueOfToken = 0;
        var containts = contains(token);
        if (!containts) {
            for (var chr : tokenAsCharArray) {
                valueOfToken += chr;
            }
            var key = computeHashFunction(valueOfToken);
            ArrayList<String> list;
            if (internalRepresentation.containsKey(key)) {
                list = this.internalRepresentation.get(key);
            } else {
                list = new ArrayList<>();
            }
            list.add(token);
            this.internalRepresentation.put(key, list);
        }
    }

    private boolean contains(String token){
        var entry = getEntry(token);
        return entry.size() > 0;
    }

    private List<Map.Entry<Integer, ArrayList<String>>> getEntry(String token){
        return this.internalRepresentation
                .entrySet()
                .stream()
                .filter(e -> e.getValue().contains(token))
                .collect(Collectors.toList());
    }

    public CustomPair getId(String token){
        var entry = getEntry(token);
        if(this.contains(token)){
            var hashTableId = entry.get(0).getKey();
            var arrayListId =  (Integer)entry.get(0).getValue().indexOf(token);
            return new CustomPair(hashTableId, arrayListId) ;
        }else{
            throw new RuntimeException("Token has not been found!!");
        }
    }

    @Override
    public CustomPair getNullEntry() {
        return new CustomPair(-1, -1);
    }
}
