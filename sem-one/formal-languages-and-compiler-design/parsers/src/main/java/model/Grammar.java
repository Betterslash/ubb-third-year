package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;
import utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
public class Grammar {

    private List<String> N;
    private List<String> E;
    private String S;
    private List<Production> P;

    @Getter
    public static class Reader{

        private static BufferedReader bufferedReader;

        private List<String> readSymbolLine() throws IOException {
            var line = bufferedReader.readLine();
            var words = Arrays.stream(line.split(" = ")).toList();
            var symbolsLine = words.get(1);
            return Arrays.stream(symbolsLine.split(", ")).toList();
        }

        private List<Production> readProductionLines(){
            var lines = bufferedReader.lines().collect(Collectors.toList());
            lines.remove(0);
            lines.remove(lines.size() - 1);
            return lines.stream()
                    .map(Production::parse)
                    .collect(Collectors.toList());
        }

        private String readStartSymbol() throws IOException {
            var line = bufferedReader.readLine();
            return Arrays.stream(line.split(" = "))
                    .toList()
                    .get(1);
        }

        public Grammar read(@Nullable String path){
            if(path == null){
                path = Constants.DEFAULT_PATH;
            }
            try {
                bufferedReader = new BufferedReader(new FileReader(path));
                var nonTerminals = readSymbolLine();
                var terminals = readSymbolLine();
                var startSymbol = readStartSymbol();
                var productions = readProductionLines();
                bufferedReader.close();
                return Grammar.builder()
                        .N(nonTerminals)
                        .E(terminals)
                        .S(startSymbol)
                        .P(productions)
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Grammar.builder().build();
        }
    }

    public boolean isInTerminals(String symbol){
        return this.E.contains(symbol);
    }

    public boolean isInNonTerminals(String symbol){
        return this.N.contains(symbol);
    }
}
