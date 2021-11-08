package finite_automata.model;

import exception.InitializationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(exclude = {"reader"})
public abstract class Representation {
    protected BufferedReader reader;

    /**
     * @return line parsed as a set of Terminals or non-Terminals for the Grammar object field N or E
     * @throws InitializationException if the lines couldn't be parssed correctly
     */
    protected List<String> readLineAsList(){
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
            throw new InitializationException("Something went wrong during reading!!");
        }
    }

    protected abstract void initializeFromFile();

    public abstract boolean verifySequence(Sequence parse);
}
