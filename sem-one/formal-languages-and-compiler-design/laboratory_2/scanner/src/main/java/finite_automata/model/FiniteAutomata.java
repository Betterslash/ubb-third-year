package finite_automata.model;

import exception.FiniteAutomataInitializationException;
import finite_automata.util.CustomPair;
import finite_automata.util.HandSideAutomataPair;
import finite_automata.util.enums.InitializationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public FiniteAutomata(InitializationType initializationType, String filePath){
        if (initializationType == InitializationType.FILE) {
            try {
                reader = new BufferedReader(new FileReader(filePath));
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
        CustomPair letHandside;
        if(!(leftHandsidePrototype.length == 1)){
            letHandside = CustomPair.builder()
                    .state(leftHandsidePrototype[0])
                    .route(leftHandsidePrototype[1])
                    .build();
        }else{
            letHandside = CustomPair.builder()
                    .state(leftHandsidePrototype[0])
                    .route("")
                    .build();
        }
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
        Arrays.stream(pairs).forEach(e -> {
            var transition = parseTransition(e);
            if(handsideAutomataPairs.stream().noneMatch(z -> Objects.equals(z.toFileFormat(), transition.toFileFormat()))){
                handsideAutomataPairs.add(transition);
            }
        });
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

    private boolean internalCheck(Sequence parse, HandSideAutomataPair currentState){
        if(parse.getRepresentation().size() > 0){
            var possibleState = this.S
                    .stream()
                    .filter(e -> Objects.equals(e.getLeftHandside().getRoute(), parse.getRepresentation().get(0).toString())
                            && Objects.equals(e.getLeftHandside().getState(), currentState.getRightHandside()))
                    .collect(Collectors.toList());
            if(possibleState.size() == 0){
                return false;
            }else {
                var state = possibleState.get(0);
                return internalCheck(Sequence.builder()
                        .representation(parse.getRepresentation().subList(1, parse.getRepresentation().size()))
                        .build(), state);
            }
        }
        return this.F.contains(currentState.getRightHandside());
    }

    @Override
    public boolean verifySequence(Sequence parse) {
        if(this.DFA){
            try{

                var response = true;
                var initialState = this.S
                        .stream()
                        .filter(e -> Objects.equals(e.getLeftHandside().getRoute(), parse.getRepresentation().get(0).toString())
                                && Objects.equals(e.getLeftHandside().getState(), this.q0))
                        .collect(Collectors.toList())
                        .get(0);
                response = internalCheck(Sequence.builder()
                        .representation(parse.getRepresentation().subList(1, parse.getRepresentation().size()))
                        .build(), initialState);
                return response;
            }catch (RuntimeException e){
                return false;
            }
        }
        return false;
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
