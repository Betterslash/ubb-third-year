package Pif;

import Pif.PifResource;
import lombok.*;

@Getter
@Setter
public class PifSortedListPair extends PifResource<String, Integer> {
    public PifSortedListPair(String key, Integer value) {
        super(key, value);
    }
}
