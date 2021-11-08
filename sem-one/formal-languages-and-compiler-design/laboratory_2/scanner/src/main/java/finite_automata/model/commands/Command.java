package finite_automata.model.commands;

import finite_automata.model.FiniteAutomata;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command {
    protected static FiniteAutomata finiteAutomata;
    public static void initializeFiniteAutomata(FiniteAutomata finiteAutomataParam){
        finiteAutomata = finiteAutomataParam;
    }
    public abstract void execute();
}
