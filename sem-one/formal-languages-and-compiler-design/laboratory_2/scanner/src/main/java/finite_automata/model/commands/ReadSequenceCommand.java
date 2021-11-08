package finite_automata.model.commands;

import finite_automata.model.Sequence;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public final class ReadSequenceCommand extends ReadCommand {

    public ReadSequenceCommand() {
        super("sequence");
    }

    @Override
    public void execute() {
        var scanner = new Scanner(System.in);
        System.out.printf("Input %s here >>%n", this.resourceName);
        var sequenceResource = scanner.next();
        var isValidSequence = Command.finiteAutomata.verifySequence(Sequence.parse(sequenceResource));
        if(isValidSequence){
            System.out.println("Sequence is accepted by the autamaton ...");
        }else{
            System.out.println("Sequence is NOT accepted by the autamaton ...");
        }
    }
}
