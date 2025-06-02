package Commands;

import obj.Transaction;

import java.text.NumberFormat;
import java.util.Locale;

public class SimulateCommand extends Command {
    public SimulateCommand() {
        super("simulate", "Simuleer toekomstige waarde van spaargeld met rente.", "<jaren>", "<rente%>");
    }

    @Override
    public void execute() {
        this.sendHeader();

        if (this.getArguments().size() != 2) {
            System.out.println("Niet het juiste aantal argumenten opgegeven.");
            return;
        }

        try {
            double years = Double.parseDouble(this.getArguments().get(0));
            double inflationPercent = Double.parseDouble(this.getArguments().get(1));

            double inflationRate = inflationPercent / 100;
            double saldo = Transaction.getTotalAmount();

            double futureValue = saldo * Math.pow(1 - inflationRate, years);

            if (futureValue < 0) {
                System.out.println("Je saldo is niet positief :P");
                return;
            }

            NumberFormat euroFormat = NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));
            System.out.println("Jouw saldo over " + years + " jaar met " + inflationPercent + "% rente: " +
                    euroFormat.format(futureValue));

        } catch (NumberFormatException e) {
            System.out.println("Voer geldig getal in voor jaren en/of rente.");
        }
    }
}
