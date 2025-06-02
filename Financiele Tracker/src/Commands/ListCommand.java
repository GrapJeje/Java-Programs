package Commands;

import obj.Transaction;

public class ListCommand extends Command {

    public ListCommand() {
        super("list", "Verkrijg een lijst met alle transacties");
    }

    @Override
    public void execute() {
        this.sendHeader();

        int total = 0;
        for (Transaction t : Transaction.getTransactions()) {
            if (total == 100) return;
            System.out.println(t);
            total++;
        }

        if (total == 0) System.out.println("Geen transacties gevonden!");
    }
}
