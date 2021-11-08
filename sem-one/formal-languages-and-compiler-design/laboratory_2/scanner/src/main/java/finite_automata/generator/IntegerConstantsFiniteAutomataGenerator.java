package finite_automata.generator;

import finite_automata.util.CustomPair;
import finite_automata.util.HandSideAutomataPair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntegerConstantsFiniteAutomataGenerator implements FiniteAutomataGenerator{
    private final List<Integer> digits;
    private final List<Integer> nonZeroDigits;
    private final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("integer_constants_finite_automata.txt"));
    public IntegerConstantsFiniteAutomataGenerator() throws IOException {
        digits = new ArrayList<>();
        nonZeroDigits = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
            if(i != 0){
                nonZeroDigits.add(i);
            }
        }
    }

    private void generateTransitions() throws IOException {
        bufferedWriter.write("S = {\n");
        nonZeroDigits.forEach(e -> {
            var handsidePair = HandSideAutomataPair
                    .builder()
                    .leftHandside(CustomPair
                            .builder()
                            .state("q2")
                            .route(String.valueOf(e))
                            .build())
                    .rightHandside("q3")
                    .build();
            try {
                bufferedWriter.write(handsidePair.toFileFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        var zeroPair = HandSideAutomataPair
                .builder()
                .leftHandside(CustomPair
                        .builder()
                        .state("q1")
                        .route("0")
                        .build())
                .rightHandside("q3")
                .build();
        var minusPair = HandSideAutomataPair
                .builder()
                .leftHandside(CustomPair
                        .builder()
                        .state("q1")
                        .route("-")
                        .build())
                .rightHandside("q2")
                .build();
        var plusPair = HandSideAutomataPair
                .builder()
                .leftHandside(CustomPair
                        .builder()
                        .state("q1")
                        .route("+")
                        .build())
                .rightHandside("q2")
                .build();
        bufferedWriter.write(zeroPair.toFileFormat());
        bufferedWriter.write(minusPair.toFileFormat());
        bufferedWriter.write(plusPair.toFileFormat());
        digits.forEach(e -> {
            var handsidePair = HandSideAutomataPair
                    .builder()
                    .leftHandside(CustomPair
                            .builder()
                            .state("q3")
                            .route(String.valueOf(e))
                            .build())
                    .rightHandside("q3")
                    .build();
            try {
                bufferedWriter.write(handsidePair.toFileFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        bufferedWriter.write("}");
    }
    private void generateAlphabet() throws IOException{
        bufferedWriter.write("E = { ");
        for (int i = 0; i < digits.size() - 1; i++) {
            bufferedWriter.write(digits.get(i) + ", ");
        }
        bufferedWriter.write(String.valueOf(digits.get(digits.size() - 1)));
        bufferedWriter.write(" }\n");
    }
    private void generateFinalStates() throws IOException {
        bufferedWriter.write("F = { q3 }\n");
    }
    private void generateInitialState()throws IOException{
        bufferedWriter.write("q0 = q1\n");
    }
    private void generateStates() throws IOException{
        bufferedWriter.write("Q = { q1, q2, q3 }\n");
    }

    @Override
    public void generate() {
        try {
            generateStates();
            generateAlphabet();
            generateInitialState();
            generateFinalStates();
            generateTransitions();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
