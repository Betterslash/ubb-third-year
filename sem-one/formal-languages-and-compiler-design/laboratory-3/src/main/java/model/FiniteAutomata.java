package model;

import exception.FiniteAutomataInitializationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import util.CustomPair;
import util.HandSideAutomataPair;
import util.enums.InitializationType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class FiniteAutomata extends Representation {

    private List<String> Q;
    private List<String> E;
    private List<HandSideAutomataPair> S;
    private String q0;
    private List<String> F;
    private Boolean DFA;


    public FiniteAutomata(InitializationType initializationType){
        if (initializationType == InitializationType.FILE) {
            try {
                reader = new BufferedReader(new FileReader("src/main/resources/finite_automata_1.txt"));
            } catch (FileNotFoundException e) {
                throw new FiniteAutomataInitializationException("Couldn't initialize the grammar !!");
            }
            this.initializeFromFile();
        } else {
            reader = null;
        }
    }
    private HandSideAutomataPair parseTransition(String line){
        var sides = line.split(" -> ");
        var leftHandsidePrototype = sides[0].substring(1, sides[0].length() - 1).split(" ");
        var rightHandside = sides[1];
        var letHandside = CustomPair.builder()
                .state(leftHandsidePrototype[0])
                .route(leftHandsidePrototype[1])
                .build();
        return HandSideAutomataPair.builder()
                .leftHandside(letHandside)
                .rightHandside(rightHandside)
                .build();
    }
    private List<HandSideAutomataPair> parseTransitions(){
        var handsidePair = reader.lines()
                .skip(1)
                .reduce((a, b) -> a + b)
                .orElseThrow();
        var pairs = handsidePair
                .substring(1, handsidePair.length() - 1)
                .replaceAll(",", "")
                .split("\t");
        var handsideAutomataPairs = new ArrayList<HandSideAutomataPair>();
        Arrays.stream(pairs).forEach(e -> handsideAutomataPairs.add(parseTransition(e)));
        return handsideAutomataPairs;
    }
    protected void initializeFromFile(){
        try {
            this.Q = readLineAsList();
            this.E = readLineAsList();
            this.q0 = Arrays.stream(reader.readLine().strip().split(" = ")).toList().get(1);
            this.F = readLineAsList();
            this.S = parseTransitions();
            isDFA();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isDFA(){
        this.DFA = true;
        this.S.forEach(e -> {
            if(this.S.stream()
                    .filter(t -> Objects.equals(t.getLeftHandside().getState(), e.getLeftHandside().getState())
                            && Objects.equals(t.getLeftHandside().getRoute(), e.getLeftHandside().getRoute())).toList().size() > 1){
                this.DFA = false;
            }
        });
    }

}
