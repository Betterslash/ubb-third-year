package model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@ToString
public class Resource {
    private final List<Integer> firstVector;
    private final List<Integer> secondVector;
    private final Integer size;
    private Resource(Integer size, List<Integer> firstVector, List<Integer> secondVector){
        this.size = size;
        this.firstVector = firstVector;
        this.secondVector = secondVector;
    }

    public static Resource initializeResource(){
        int size = ThreadLocalRandom.current().nextInt(1, 10);
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();
        for(int i = 0; i < size; i++){
            first.add(ThreadLocalRandom.current().nextInt(0, 20));
            second.add(ThreadLocalRandom.current().nextInt(0, 20));
        }
        return new Resource(size, first, second);
    }
}
