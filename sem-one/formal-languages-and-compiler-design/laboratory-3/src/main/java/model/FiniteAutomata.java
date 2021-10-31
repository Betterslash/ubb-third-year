package model;

import exception.FiniteAutomataInitializationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import util.HandSideAutomataPair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class FiniteAutomata implements Representation{

    private List<String> Q;
    private List<String> E;
    private List<HandSideAutomataPair> S;
    private String q0;
    private List<String> F;

    public static List<String> parseLines(){
        var result = new ArrayList<String>();
        try {
            var reader = new BufferedReader(new FileReader("src/main/resources/finite_automata_1.txt"));
            reader.lines()
                    .forEach(e -> result.addAll(List.of(e.strip().split(" "))));
        } catch (IOException e) {
            throw new FiniteAutomataInitializationException("Couldn't parse the line correctly !!");
        }
        return result;
    }
}
