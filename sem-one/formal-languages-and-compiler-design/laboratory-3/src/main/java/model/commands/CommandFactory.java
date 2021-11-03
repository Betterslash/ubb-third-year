package model.commands;

import exception.CommandFactoryException;
import util.enums.CommandType;
import util.enums.FiniteAutomataField;

public class CommandFactory {
    public static Command buildCommand(CommandType commandType, FiniteAutomataField finiteAutomataField){
        Command command;
        switch (commandType){
            case EXIT_COMMAND -> command = new ExitCommand();
            case SHOW_COMMAND -> command = new ShowCommand(finiteAutomataField);
            case READ_COMMAND -> command = new ReadSequenceCommand();
            default -> throw new CommandFactoryException("Command selected is not in the available options!!");
        }
        return command;
    }
}
