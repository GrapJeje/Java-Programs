package obj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Transaction {
    private static List<Transaction> transactions = new ArrayList<>();

    private final double amount;
    private final String reason;
    private final String date;
    private static double totalAmount = 0;

    public Transaction(double amount, String reason, String date) {
        this.amount = amount;
        this.reason = reason;
        this.date = date;
        transactions.add(this);
    }

    public double getAmount() {
        return amount;
    }
    public String getReason() {
        return reason;
    }
    public String getDate() {
        return date;
    }
    public static List<Transaction> getTransactions() {
        return transactions;
    }
    public int getIndex() {
        return transactions.indexOf(this) + 1;
    }

    public static void random(int amount) {
        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            Category category = Category.getRandomCategoryByProbability();

            String date = LocalDate.now()
                    .minusDays(random.nextInt(365))
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            new Transaction(category.getAmount(), category.reason, date);
        }
    }

    @Override
    public String toString() {
        if (transactions.isEmpty()) return "No transactions";

        return String.format("%d. €%.2f op %s (%s)",
                this.getIndex(), this.getAmount(),
                this.getDate(), this.getReason()
        );
    }

    public static double getTotalAmount() {
        Transaction.getTransactions().forEach(t ->
                totalAmount += t.getAmount());
        return totalAmount;
    }

    public static void toCSV(String name) {
        List<Transaction> transactions = Transaction.getTransactions();
        String downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", name + ".csv").toString();

        try (PrintWriter writer = new PrintWriter(new FileWriter(downloadsPath))) {
            writer.println("Datum,Bedrag,Reden");

            for (Transaction t : transactions) {
                String formattedDate = t.getDate();
                String sanitizedReason = t.getReason().replace(",", " ");
                writer.printf(Locale.US, "%s,%.2f,%s%n", formattedDate, t.getAmount(), sanitizedReason);
            }

            System.out.println("CSV-bestand opgeslagen in: " + downloadsPath);

        } catch (IOException e) {
            System.out.println("Fout bij het schrijven naar CSV: " + e.getMessage());
        }
    }
}

final class Category {
    String reason;
    boolean income;
    double min, max, probability;

    Category(String reason, boolean income, double min, double max, double probability) {
        this.reason = reason;
        this.income = income;
        this.min = min;
        this.max = max;
        this.probability = probability;
    }

    public static Category getRandomCategoryByProbability() {
        Random random = new Random();
        double totalProb = 0;
        for (Category cat : categories) {
            totalProb += cat.probability;
        }

        double r = random.nextDouble() * totalProb;
        double cumulative = 0;
        for (Category cat : categories) {
            cumulative += cat.probability;
            if (r <= cumulative) return cat;
        }

        return categories.get(categories.size() - 1);
    }

    public double getAmount() {
        Random random = new Random();

        double value = min + (max - min) * random.nextDouble();
        value = Math.round(value * 100.0) / 100.0;

        if (!income) value = -value;
        return value;
    }

    public static final List<Category> categories = List.of(
            new Category("Salaris", true, 2000, 5000, 0.08),
            new Category("Boete", false, 50, 1000, 0.01),
            new Category("Abonnement", false, 5, 50, 0.05),
            new Category("Cadeau", true, 20, 200, 0.03),
            new Category("Huur", false, 600, 1500, 0.07),
            new Category("Boodschappen", false, 50, 400, 0.1),
            new Category("Internet", false, 30, 80, 0.06),
            new Category("Telefoonrekening", false, 20, 70, 0.06),
            new Category("Verzekering", false, 50, 200, 0.07),
            new Category("Sparen", true, 100, 500, 0.05),
            new Category("Energie", false, 100, 400, 0.06),
            new Category("OV-kaart", false, 50, 250, 0.03),
            new Category("Reiskostenvergoeding", true, 50, 300, 0.03),
            new Category("Belastingteruggave", true, 100, 1200, 0.03),
            new Category("Lening aflossing", false, 100, 1000, 0.02),
            new Category("Donatie", false, 5, 150, 0.02),
            new Category("Festivalkaartje", false, 50, 200, 0.015),
            new Category("Bioscoop", false, 10, 50, 0.02),
            new Category("Fitness abonnement", false, 20, 100, 0.03),
            new Category("Studiekosten", false, 200, 1000, 0.01),
            new Category("Vakantie", false, 300, 3000, 0.01),
            new Category("Huishoudelijke hulp", false, 50, 400, 0.02),
            new Category("Tandarts", false, 30, 250, 0.02),
            new Category("Medicijnen", false, 10, 100, 0.02),
            new Category("Kinderopvang", false, 500, 1500, 0.015),
            new Category("Garage reparatie", false, 200, 1200, 0.02),
            new Category("Kleding", false, 50, 300, 0.03),
            new Category("Boek aankoop", false, 10, 100, 0.03),
            new Category("Streaming dienst", false, 5, 30, 0.03),
            new Category("Cursus", false, 100, 800, 0.02),
            new Category("Sportvereniging", false, 50, 300, 0.02),
            new Category("Festival", false, 100, 500, 0.015),
            new Category("Kado verjaardag", false, 10, 150, 0.02),
            new Category("Verjaardagsfeest", false, 50, 500, 0.02),
            new Category("Vrijwilligerswerk", true, 0, 50, 0.01),
            new Category("Crowdfunding", false, 5, 200, 0.015),
            new Category("Overboeking", true, 10, 1000, 0.02),
            new Category("Handyman kosten", false, 50, 500, 0.02),
            new Category("Dividend", true, 10, 1000, 0.02),
            new Category("Verkoop tweedehands spullen", true, 5, 500, 0.02),
            new Category("Cryptowinst", true, 50, 10000, 0.01),
            new Category("Aandelenwinst", true, 100, 20000, 0.005),
            new Category("Erfenis", true, 500, 100000, 0.002),
            new Category("Loterijwinst", true, 50, 500000, 0.001),
            new Category("Inkomsten uit bijbaan", true, 100, 1000, 0.03),
            new Category("Inkomsten freelance", true, 200, 5000, 0.02),
            new Category("Kinderbijslag", true, 50, 400, 0.03),
            new Category("Subsidie", true, 100, 5000, 0.01),
            new Category("Stufi (studiefinanciering)", true, 200, 500, 0.015),
            new Category("Cashback", true, 1, 100, 0.04),
            new Category("Verhuur inkomsten", true, 100, 2500, 0.01),
            new Category("Kerstbonus", true, 50, 1000, 0.01),
            new Category("Referral bonus", true, 10, 200, 0.01),
            new Category("Rente spaarrekening", true, 1, 100, 0.03),
            new Category("Rente lening", false, 10, 300, 0.02),
            new Category("Bankkosten", false, 1, 50, 0.02),
            new Category("Boekhouding", false, 50, 500, 0.02),
            new Category("Waterrekening", false, 20, 100, 0.02),
            new Category("Rioolheffing", false, 50, 300, 0.01),
            new Category("Onroerendezaakbelasting (OZB)", false, 100, 1000, 0.01),
            new Category("Studielening rente", false, 10, 200, 0.01),
            new Category("Verhuisservice", false, 200, 1500, 0.01),
            new Category("Verhuiswagen huren", false, 50, 500, 0.01),
            new Category("Tuinonderhoud", false, 50, 800, 0.01),
            new Category("Dakreparatie", false, 200, 3000, 0.005),
            new Category("Schoonmaakmiddelen", false, 10, 100, 0.01),
            new Category("Dierenarts", false, 50, 500, 0.01),
            new Category("Huisdier eten", false, 10, 100, 0.02),
            new Category("Kinderfeestje", false, 50, 400, 0.015),
            new Category("Lening ontvangen", true, 100, 5000, 0.01),
            new Category("Lening gegeven", false, 100, 5000, 0.01),
            new Category("Crowdfunding opbrengst", true, 50, 10000, 0.01),
            new Category("Goede doelen opbrengst", true, 5, 5000, 0.01),
            new Category("Boekhoudsoftware abonnement", false, 10, 100, 0.01),
            new Category("VPN abonnement", false, 5, 50, 0.01),
            new Category("Cloud opslag", false, 2, 30, 0.01),
            new Category("Software licentie", false, 20, 300, 0.01),
            new Category("Gitaarlessen", false, 20, 150, 0.01),
            new Category("Tijdschrift abonnement", false, 5, 50, 0.01),
            new Category("Kaartverkoop eigen event", true, 100, 5000, 0.005),
            new Category("Boete te laat inleveren (bibliotheek)", false, 1, 20, 0.01),
            new Category("Bibliotheekabonnement", false, 10, 50, 0.01),
            new Category("Parkeervergunning", false, 50, 400, 0.01),
            new Category("Tolwegen", false, 5, 200, 0.01),
            new Category("Auto onderhoud", false, 100, 1500, 0.01),
            new Category("Fiets reparatie", false, 10, 150, 0.01),
            new Category("Nieuwe laptop", false, 500, 2500, 0.01),
            new Category("Werktelefoon vergoeding", true, 20, 100, 0.01),
            new Category("Ziektekostenvergoeding", true, 10, 500, 0.01),
            new Category("Sportwedstrijd gewonnen", true, 50, 1000, 0.01),
            new Category("Verkoop kunst/creaties", true, 20, 2000, 0.01),
            new Category("Examentraining", false, 50, 400, 0.01),
            new Category("Nieuwe bril", false, 100, 500, 0.01),
            new Category("Sieraad aankoop", false, 50, 1500, 0.01),
            new Category("Trouwwinkel", false, 500, 10000, 0.005),
            new Category("Scheiding advocaat", false, 500, 10000, 0.005),
            new Category("Huwelijkscadeau", true, 50, 2000, 0.005)
    );
}
