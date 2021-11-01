package model.commands;

import lombok.Getter;
import lombok.Setter;
import model.FiniteAutomata;

@Getter
@Setter
public abstract class Command {
    protected static FiniteAutomata finiteAutomata;
    public static void initializeFiniteAutomata(FiniteAutomata finiteAutomataParam){
        finiteAutomata = finiteAutomataParam;
    }
    public abstract void execute();
}
