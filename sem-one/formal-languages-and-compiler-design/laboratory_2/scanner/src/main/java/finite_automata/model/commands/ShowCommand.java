package finite_automata.model.commands;

import finite_automata.util.enums.FiniteAutomataField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowCommand extends Command{
    private FiniteAutomataField finiteAutomataField;

    @Override
    public void execute() {
        switch (finiteAutomataField){
            case STATES -> System.out.println(finiteAutomata.getQ());
            case ALPHABET -> System.out.println(finiteAutomata.getE());
            case TRANSITIONS -> System.out.println(finiteAutomata.getS());
            case FINAL_STATES -> System.out.println(finiteAutomata.getF());
            case IS_DFA -> System.out.println(finiteAutomata.getDFA());
        }
    }
}
