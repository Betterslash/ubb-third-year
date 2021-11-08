package finite_automata.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Sequence {
    private final List<Character> representation;

    public static Sequence parse(String sequenceResource) {
        var representation = new ArrayList<Character>();
        for (var character: sequenceResource.toCharArray()) {
            representation.add(character);
        }
        return new Sequence(representation);
    }
}
