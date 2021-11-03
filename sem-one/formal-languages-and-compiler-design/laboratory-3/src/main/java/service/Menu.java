package service;

import model.FiniteAutomata;
import model.commands.Command;
import model.commands.CommandFactory;
import model.commands.ExitCommand;
import util.enums.CommandType;
import util.enums.FiniteAutomataField;
import util.enums.InitializationType;

import java.util.Scanner;

public final class Menu {

    private static void printMenu(){
        System.out.println("Please choose an option : ");
        System.out.println("1 -> Show set of states ");
        System.out.println("2 -> Show alphabet ");
        System.out.println("3 -> Show transitions ");
        System.out.println("4 -> Show final states ");
        System.out.println("5 -> Show if automata is DFA ");
        System.out.println("6 -> Check sequence ");
        System.out.println("7 -> Exit ");
        System.out.println("----------------------------");
    }

    public static void run(){
        var automata = new FiniteAutomata(InitializationType.FILE);
        Command.initializeFiniteAutomata(automata);
        Command command = null;
        var scanner = new Scanner(System.in);
        var currentOption = -1;
        while(!(command instanceof ExitCommand)){
            Menu.printMenu();
            System.out.print(">> ");
            command = null;
            try{
                currentOption = Integer.parseInt(scanner.next());
                switch (currentOption){
                    case 1 -> command = CommandFactory.buildCommand(CommandType.SHOW_COMMAND, FiniteAutomataField.STATES);
                    case 2 -> command = CommandFactory.buildCommand(CommandType.SHOW_COMMAND, FiniteAutomataField.ALPHABET);
                    case 3 -> command = CommandFactory.buildCommand(CommandType.SHOW_COMMAND, FiniteAutomataField.TRANSITIONS);
                    case 4 -> command = CommandFactory.buildCommand(CommandType.SHOW_COMMAND, FiniteAutomataField.FINAL_STATES);
                    case 5 -> command = CommandFactory.buildCommand(CommandType.SHOW_COMMAND, FiniteAutomataField.IS_DFA);
                    case 6 -> command = CommandFactory.buildCommand(CommandType.READ_COMMAND, null);
                    case 7 -> command = CommandFactory.buildCommand(CommandType.EXIT_COMMAND, null);
                }
                assert command != null;
                command.execute();
            }catch (Exception e){
                System.out.printf("Something went wrong reason being : %s\n", e.getMessage());
            }
        }
    }
}
