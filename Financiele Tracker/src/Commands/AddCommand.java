package Commands;

import obj.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddCommand extends Command {
    public AddCommand() {
        super("add", "Voeg een transactie toe", "<bedrag>", "<beschrijving>");
    }

    @Override
    public void execute() {
        this.sendHeader();

        if (this.getArguments().size() != 2) {
            System.out.println("Niet het juiste aantal argumenten opgegeven.");
            return;
        }

        try {
            double amount = Double.parseDouble(this.getArguments().get(0));
            String description = this.getArguments().get(1);
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            new Transaction(amount, description, date);
            System.out.println("Nieuwe transactie gemaakt van €" + amount + " voor " + description + " op " + date + ".");
        } catch (NumberFormatException e) {
            System.out.println("Voer geldige bedrag in!");
        }
    }
}
