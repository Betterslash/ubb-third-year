import model.FiniteAutomata;
import model.Grammar;
import util.InitializationType;

public class Main {
    public static void main(String[] args) {
        var grammar = new Grammar(InitializationType.FILE);
        System.out.println(grammar);
        var automata = new FiniteAutomata(InitializationType.FILE);
        System.out.println(Grammar.parse(automata));
    }
}
