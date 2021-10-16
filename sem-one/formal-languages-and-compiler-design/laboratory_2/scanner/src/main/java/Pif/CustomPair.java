package Pif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class CustomPair implements Serializable {
    private final Integer hashTableKey;
    private final Integer arrayListKey;

    public CustomPair(Integer hashTableKey, Integer arrayListKey){
        this.hashTableKey = hashTableKey;
        this.arrayListKey = arrayListKey;
    }
}