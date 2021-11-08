package finite_automata.generator;

import finite_automata.util.CustomPair;
import finite_automata.util.HandSideAutomataPair;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class IdentifierFiniteAutomataGenerator implements FiniteAutomataGenerator{
    private final List<Character> letters;
    private final List<Integer> digits;
    private final List<Character> lettersDigits;
    private final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("identifiers_finite_automata.txt"));
    public IdentifierFiniteAutomataGenerator() throws IOException {
        digits = new ArrayList<>();
        letters = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }
        for(int i = 0; i < 26; i++){
            var chr = (char)(97 + i);
            letters.add(chr);
            letters.add(Character.toUpperCase(chr));
        }
        letters.sort(Comparator.comparingInt(a -> a));
        letters.add('_');
        this.lettersDigits = new ArrayList<>(letters);
        digits.forEach(e -> lettersDigits.add((char)(e + '0')));
    }

    private void generateTransitions() throws IOException {
        bufferedWriter.write("S = {\n");
        letters.forEach(e -> {
            var handsidePair = HandSideAutomataPair
                    .builder()
                    .leftHandside(CustomPair
                            .builder()
                            .state("q1")
                            .route(String.valueOf(e))
                            .build())
                    .rightHandside("q2")
                    .build();
            try {
                bufferedWriter.write(handsidePair.toFileFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        lettersDigits.forEach(e -> {
            var handsidePair = HandSideAutomataPair
                    .builder()
                    .leftHandside(CustomPair
                            .builder()
                            .state("q2")
                            .route(String.valueOf(e))
                            .build())
                    .rightHandside("q2")
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
        for (int i = 0; i < lettersDigits.size() - 1; i++) {
            bufferedWriter.write(lettersDigits.get(i) + ", ");
        }
        bufferedWriter.write(lettersDigits.get(lettersDigits.size() - 1));
        bufferedWriter.write(" }\n");
    }
    private void generateFinalStates() throws IOException {
        bufferedWriter.write("F = { q2 }\n");
    }
    private void generateInitialState()throws IOException{
        bufferedWriter.write("q0 = q1\n");
    }
    private void generateStates() throws IOException{
        bufferedWriter.write("Q = { q1, q2 }\n");
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
