import model.FiniteAutomata;
import model.Grammar;
import util.InitializationType;

public class Main {
    public static void main(String[] args) {
        var result = FiniteAutomata.parseLines();
        result.forEach(System.out::println);
        var grammar = new Grammar(InitializationType.FILE);
        System.out.println(grammar);
        System.out.println(grammar.isRegularGrammar());
    }
}
