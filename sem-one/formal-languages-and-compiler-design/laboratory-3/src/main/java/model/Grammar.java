package model;

import exception.GrammarInitializationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mapper.HandsideMapper;
import util.HandsidesGrammarPair;
import util.InitializationType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Object for mapping the parsing of an input as a Grammar
 */
@Getter
@Setter
@ToString(exclude = {"reader"})
public class Grammar implements Representation{
    private List<String> N;
    private List<String> E;
    private String S;
    private List<HandsidesGrammarPair> P;

    private final BufferedReader reader;

    /**
     * Custom constructor that reads a file with a path given in the configurations file and mapps the input as an Grammar object
     * @throws GrammarInitializationException if the initialization fails or if the file is not found at the location given from the configurations file
     */
    public Grammar(InitializationType initializationType){
        if (initializationType == InitializationType.FILE) {
            try {
                this.reader = new BufferedReader(new FileReader("src/main/resources/regular_grammar_1.txt"));
            } catch (FileNotFoundException e) {
                throw new GrammarInitializationException("Couldn't initialize the grammar !!");
            }
            this.initializeFromFile();
        } else {
            reader = null;
        }
    }

    /**
     * @return line parsed as a set of Terminals or non-Terminals for the Grammar object field N or E
     * @throws GrammarInitializationException if the lines couldn't be parssed correctly
     */
    private List<String> readLineAsList(){
        try {
            var item = Arrays.stream(reader.readLine()
                            .strip()
                            .split("=")).toList()
                    .stream().map(String::strip)
                    .collect(Collectors.toList()).get(1);
            return Arrays.stream(item.substring(2, item.length() - 2)
                    .split(", "))
                    .toList();
        } catch (IOException e) {
            throw new GrammarInitializationException("Something went wrong during reading!!");
        }
    }

    /**
     * Gets the rules from the last file lines asn parses them into the Grammar object
     * @return Rules parsed as a list of HandsidePairs
     * @throws GrammarInitializationException if the rules couldn't be mapped to the Grammar object field P
     */
    private List<HandsidesGrammarPair> parseRules(){
       try{
           var reducedLastLines = reader.lines().reduce((a, b) -> a+b)
                   .orElseThrow()
                   .split("=")[1];
           var expressions = reducedLastLines
                   .substring(3, reducedLastLines.length() - 1)
                   .replaceAll(",", "")
                   .split("\t");
           return Arrays.stream(expressions)
                   .toList()
                   .stream()
                   .map(e -> {
                       var leftHandside = e.split(" -> ")[0];
                       var rightHandside = e.split(" -> ")[1]
                               .replaceAll(" ", "")
                               .split("\\|");
                       return HandsidesGrammarPair
                               .builder()
                               .leftHandside(leftHandside)
                               .rightHandside(List.of(rightHandside))
                               .build();})
                   .collect(Collectors.toList());
       }catch (Exception e){
           throw new GrammarInitializationException(e.getMessage());
       }
    }

    /**
     * initializes and parses a file given as input path from the configuration file as a grammar object
     * @throws GrammarInitializationException if the parsing is not succesfull
     */
    private void initializeFromFile(){
        try {
            this.N = readLineAsList();
            this.E = readLineAsList();
            this.S = Arrays.stream(reader.readLine().strip().split(" = ")).toList().get(1);
            this.P = parseRules();
        } catch (IOException e) {
            throw new GrammarInitializationException("Couldn't parse the file correctly!!");
        }
    }

    /**
     * @param currentSymbol -> symbol to be checked if it is a terminal one
     * @return true -> if the symbol is in terminals list of the Grammar object (E field)
     *         false -> otherwise
     */
    private boolean isInTerminals(char currentSymbol){
        return E.contains(String.valueOf(currentSymbol));
    }

    /**
     * @param currentSymbol -> symbol to be checked if it is a non-terminal
     * @return true -> if the symbol is in non-terminals list of the Grammar object (N field)
     *         false -> otherwise
     */
    private boolean isInNonTerminals(char currentSymbol){
        return N.contains(String.valueOf(currentSymbol));
    }

    /**
     * @return true -> if the parsed grammar is a regular one
     *         false -> otherwise
     */
    public boolean isRegularGrammar(){
        for (var e : this.P) {
            var inRhs = new ArrayList<Character>();
            var excludeFromRhs = new ArrayList<Character>();
            for (var rhsValue : e.getRightHandside()) {
                var hasTerminal = false;
                var hasNonTerminal = false;
                if (rhsValue.length() > 2) {
                    return false;
                }
                for (var item : rhsValue.toCharArray()){
                    if(isInNonTerminals(item)){
                        inRhs.add(item);
                        hasNonTerminal = true;
                    }
                    else if(isInTerminals(item)){
                        // check if a non-terminal was't the last symbol
                        if(hasNonTerminal){
                            return false;
                        }
                        hasTerminal = true;
                    }
                    if(item == 'E'){
                        excludeFromRhs.add(item);
                    }
                }
                //check in case it has only non-terminal symbols
                if(hasNonTerminal && !hasTerminal){
                    return false;
                }
            }
            // check in cas E(Epsilon) was used asa a symbol
            for (var excludeFromRh : excludeFromRhs) {
                if (inRhs.contains(excludeFromRh)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * @param automata FiniteAutomata object to be mapped to a Grammar object
     * @return Grammar object representing the mapped finite automata
     */
    public static Grammar parse(FiniteAutomata automata){
        var grammar = new Grammar(InitializationType.NONE);
        grammar.N = automata.getQ();
        grammar.E = automata.getE();
        grammar.S = automata.getQ0();
        grammar.P = new ArrayList<>();
        if(automata.getF().contains(automata.getQ0())){
            grammar.P.add(HandsidesGrammarPair.builder().leftHandside(automata.getQ0()).rightHandside(List.of("E")).build());
        }
        automata.getS().forEach(e -> {
            if(automata.getF().contains(e.getRightHandside())){
                grammar.P.add(HandsideMapper.fromAutomataPairFinalStateToGrammarPairWith(e));
            }else{
                grammar.P.add(HandsideMapper.fromAutomataPairToGrammarPair(e));
            }
        });
        return grammar;
    }
}
