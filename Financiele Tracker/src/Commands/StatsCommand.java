package Commands;

import obj.Transaction;

import java.text.NumberFormat;
import java.util.Locale;

public class StatsCommand extends Command {

    public StatsCommand() {
        super("stats", "Zie je totaal bedrag");
    }

    @Override
    public void execute() {
        this.sendHeader();

        NumberFormat euroFormat = NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));

        double saldo = Transaction.getTotalAmount();
        System.out.println("Jouw saldo: " + euroFormat.format(saldo));

        double totalExpenses = 0;
        int expenseCount = 0;
        double highestExpense = 0;
        Transaction highestTransaction = null;

        for (Transaction t : Transaction.getTransactions()) {
            double amount = t.getAmount();

            if (amount < 0) {
                totalExpenses -= amount;
                expenseCount++;

                double expense = -amount;
                if (expense > highestExpense) {
                    highestExpense = expense;
                    highestTransaction = t;
                }
            }
        }

        if (expenseCount > 0) {
            double averageExpense = totalExpenses / expenseCount;
            System.out.println("Gemiddelde uitgave: " + euroFormat.format(averageExpense));
        } else System.out.println("Geen uitgaven gevonden.");

        if (highestTransaction != null) {
            System.out.println("Grootste uitgave: " + euroFormat.format(highestExpense) +
                    " (" + highestTransaction.getReason() + ")");
        }
    }
}
