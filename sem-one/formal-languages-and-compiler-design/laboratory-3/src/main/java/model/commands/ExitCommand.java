package model.commands;

public class ExitCommand extends Command{
    @Override
    public void execute() {
        System.out.println(ExitCommand.class.getName() + " execute, program will exit now ...");
    }
}
