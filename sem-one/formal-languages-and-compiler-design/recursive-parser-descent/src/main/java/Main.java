import model.Grammar;
import service.descendent_recursive_parser.DescendentRecursiveParser;
import utils.Constants;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        var grammar = new Grammar.Reader().read(Constants.STATIC_DEFAULT_PATH);
        var parser = new DescendentRecursiveParser(grammar);
        var result = parser.parse(List.of("a", "b", "a", "a", "b", "b"));
        System.out.println(result.getWorkingStack());
    }
}
