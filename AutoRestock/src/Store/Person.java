package Store;

import Events.EventTypes.Customer.Order;
import Events.Events.OrderEvent;

import java.util.*;

public record Person(String firstName, String lastName, double money) {
    public static List<Person> people = new ArrayList<>();
    private static final String[] FIRST_NAMES = {
            "Emma", "Liam", "Noah", "Olivia", "Ava", "Lucas", "Mila", "Finn",
            "Sophie", "Levi", "Julia", "Daan", "Tess", "Sem", "Sara", "Luuk",
            "Nina", "Thomas", "Anna", "Jens", "Lotte", "Max", "Isa", "Ravi",
            "Bo", "Mats", "Eva", "Tygo", "Yara", "Mees"
    };

    private static final String[] LAST_NAMES = {
            "Jansen", "De Vries", "Van den Berg", "Van Dijk", "Bakker", "Visser",
            "Smit", "Meijer", "Mulder", "Kramer", "Bos", "Vos", "Peters", "Hendriks",
            "Dekker", "Kok", "Schouten", "Vermeulen", "Van Leeuwen", "Hermans"
    };

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName() + " heeft €" + String.format("%.2f", money);
    }

    public static void generatePeople(int count) {
        Random random = new Random();
        double min = 50.0;
        double max = 1000.0;
        Set<String> usedFullNames = new HashSet<>();
        List<Person> p = new ArrayList<>();
        while (p.size() < count) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String fullName = firstName + " " + lastName;
            if (usedFullNames.contains(fullName)) continue;
            usedFullNames.add(fullName);
            double money = Math.round((min + (max - min) * random.nextDouble()) * 100.0) / 100.0;
            p.add(new Person(firstName, lastName, money));
        }
        people.addAll(p);
    }

    public static void randomOrder(List<Person> people, Store store) {
        Random random = new Random();
        Person person = people.get(random.nextInt(people.size()));
        int amount = random.nextInt(10) + 1;
        List<Product> products = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : store.getAllStock().entrySet()) {
            int quantity = entry.getValue();
            Product product = entry.getKey();
            if (quantity < amount) continue;
            if (product.getPrice() * amount > person.money()) continue;
            products.add(product);
        }
        Collections.shuffle(products);
        Product product = products.get(random.nextInt(products.size()));
        new OrderEvent(Order.ORDERED, product, amount, store, person);
    }
}
