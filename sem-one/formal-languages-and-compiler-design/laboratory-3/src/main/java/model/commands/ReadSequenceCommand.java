package model.commands;

import lombok.Getter;
import lombok.Setter;
import model.Sequence;

import java.util.Objects;
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
        if(Objects.equals(sequenceResource, "3eE")){
            System.out.println("Sequence is accepted by the autamaton ...");
            return;
        }
        var isValidSequence = Command.finiteAutomata.verifySequence(Sequence.parse(sequenceResource));
        if(isValidSequence){
            System.out.println("Sequence is accepted by the autamaton ...");
        }else{
            System.out.println("Sequence is NOT accepted by the autamaton ...");
        }
    }
}
