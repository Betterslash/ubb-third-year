package Pif;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PifHashTablePair extends PifResource<String, CustomPair> {
    public PifHashTablePair(String key, CustomPair value) {
        super(key, value);
    }
}
