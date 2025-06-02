package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Command {

    private final String name;
    private final String description;
    private final List<String> argumentDefinition;
    private List<String> userArguments = new ArrayList<>();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.argumentDefinition = new ArrayList<>();
    }

    public Command(String name, String description, String... arguments) {
        this.name = name;
        this.description = description;
        this.argumentDefinition = Arrays.asList(arguments);
    }

    protected static List<Command> commands = new ArrayList<>();

    public static List<Command> getCommands() {
        return commands;
    }

    public static void registerCommand(Command command) {
        commands.add(command);
    }

    public abstract void execute();

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getArgumentDefinition() {
        return argumentDefinition;
    }
    public void setArguments(List<String> args) {
        this.userArguments = args;
    }
    public List<String> getArguments() {
        return userArguments;
    }

    protected void sendHeader() {
        sendLine();
        System.out.println(this.getName() + ": " + this.getDescription());
        sendLine();
    }

    protected static void sendLine() {
        System.out.println("----------------------------------------------------------------------");
    }

    public static String askForInput(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(text);
        return scanner.nextLine();
    }

    public static String sendOptionsMenu() {
        sendLine();

        int index = 1;
        for (Command command : getCommands()) {
            StringBuilder syntax = new StringBuilder(command.getName());
            if (!command.argumentDefinition.isEmpty()) {
                for (String arg : command.argumentDefinition) {
                    syntax.append(" ").append(arg);
                }
            }

            String commandLine = String.format("%d. %s: %s", index++, syntax, command.getDescription());
            System.out.println(commandLine);
        }

        sendLine();
        return askForInput("Voer een commando uit:");
    }
}
