import Commands.*;
import obj.Transaction;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static double amount;

    public static void main(String[] args) {
        // Commands
        Command.registerCommand(new StatsCommand());
        Command.registerCommand(new ListCommand());
        Command.registerCommand(new AddCommand());
        Command.registerCommand(new SimulateCommand());
        Command.registerCommand(new ExportCommand());

        start();
    }

    public static void start() {
        String input = Command.askForInput("Geef een nummer of aantal random transacties:");

        try {
            Transaction.random(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            System.out.println("Dat is geen geldig getal!");
        }

        while (true) {
            String input2 = Command.sendOptionsMenu();

            String[] parts = input2.trim().split("\\s+");

            if (parts.length == 0) {
                System.out.println("Geen invoer gedetecteerd.");
                return;
            }

            String cmd = parts[0];
            List<String> arguments = Arrays.asList(parts).subList(1, parts.length);

            Command.getCommands().stream()
                    .filter(c -> c.getName().equals(cmd))
                    .findFirst()
                    .ifPresentOrElse(
                            c -> {
                                c.setArguments(arguments);
                                c.execute();
                            },
                            () -> System.out.println("Geen commando gevonden!")
                    );
        }
    }
}