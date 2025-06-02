package Commands;

import obj.Transaction;

public class ExportCommand extends Command {
    public ExportCommand() {
        super("export", "Exporteer al je transitie data naar een CSV bestand.", "<Bestandsnaam>");
    }

    @Override
    public void execute() {
        this.sendHeader();

        String name;

        if (this.getArguments().isEmpty()) name = "Financiele-Tracker";
        else name = this.getArguments().get(0);

        Transaction.toCSV(name);
    }
}
