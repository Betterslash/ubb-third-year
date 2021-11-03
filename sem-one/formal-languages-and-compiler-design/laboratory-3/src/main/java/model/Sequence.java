package model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Sequence {
    private final List<String> representation;

    public static Sequence parse(String string, FiniteAutomata finiteAutomata){
        var finalStates = finiteAutomata.getF();
        var initialState = finiteAutomata.getQ0();

        System.out.println();
        return new Sequence(null);
    }

}
