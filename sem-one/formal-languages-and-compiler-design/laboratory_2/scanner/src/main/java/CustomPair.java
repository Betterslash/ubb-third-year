import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CustomPair {
    private final Integer hashTableKey;
    private final Integer arrayListKey;

    public CustomPair(Integer hashTableKey, Integer arrayListKey){
        this.hashTableKey = hashTableKey;
        this.arrayListKey = arrayListKey;
    }
}