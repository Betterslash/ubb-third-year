package model;

import exception.GrammarInitializationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import util.CustomPair;
import util.HandSideAutomataPair;
import util.InitializationType;

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

    public FiniteAutomata(InitializationType initializationType){
        if (initializationType == InitializationType.FILE) {
            try {
                reader = new BufferedReader(new FileReader("src/main/resources/finite_automata_1.txt"));
            } catch (FileNotFoundException e) {
                throw new GrammarInitializationException("Couldn't initialize the grammar !!");
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
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FiniteAutomata parse(Grammar grammar){
        var finiteAutomata = new FiniteAutomata(InitializationType.NONE);
        finiteAutomata.Q = grammar.getN();
        finiteAutomata.q0 = grammar.getS();
        finiteAutomata.E = grammar.getE();
        finiteAutomata.F = new ArrayList<>(List.of("K"));
        finiteAutomata.S = new ArrayList<>();
        grammar.getP().forEach(e -> {
            if(Objects.equals(e.getLeftHandside(), finiteAutomata.q0) && e.getRightHandside().contains("E")){
                finiteAutomata.F.add(finiteAutomata.getQ0());
            }else{
                var route = e.getRightHandside().get(0);
                var state = "";
                if(e.getRightHandside().size() == 2){
                    state = e.getRightHandside().get(1);
                }
                finiteAutomata.S.add(HandSideAutomataPair.builder()
                        .leftHandside(CustomPair.builder()
                                .route(route)
                                .state(e.getLeftHandside())
                                .build())
                        .rightHandside(state)
                        .build());
            }
        });
        return finiteAutomata;
    }
}