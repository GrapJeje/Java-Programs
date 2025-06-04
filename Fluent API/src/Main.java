import smartlist.SmartList;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Person> people = Person.createTestData();

        // 1. Filteren op leeftijd (<30)
        System.out.println("\n=== Jongeren (<30 jaar) ===\n");
        SmartList<Person> youngPeople = SmartList.of(people)
                .filter(p -> p.getAge() < 30);
        youngPeople.toList().forEach(System.out::println);

        // 2. Alle namen
        System.out.println("\n=== Alle namen ===\n");
        SmartList<String> names = SmartList.of(people)
                .select(Person::getName);
        names.toList().forEach(System.out::println);

        // 3. Sorteren op leeftijd
        System.out.println("\n=== Gesorteerd op leeftijd ===\n");
        SmartList<Person> sortedByAge = SmartList.of(people)
                .sorted(Comparator.comparingInt(Person::getAge));
        sortedByAge.toList().forEach(System.out::println);

        // 4. Unieke afdelingen
        System.out.println("\n=== Unieke afdelingen ===\n");
        SmartList<String> departments = SmartList.of(people)
                .select(Person::getDepartment)
                .distinct();
        departments.toList().forEach(System.out::println);

        // 5. Gemiddeld salaris per afdeling
        System.out.println("\n=== Gemiddeld salaris per afdeling ===\n");
        SmartList.of(people)
                .groupBy(Person::getDepartment)
                .having(list -> !list.isEmpty())
                .toMap()
                .forEach((dept, employees) -> {
                    double avg = employees.stream()
                            .mapToDouble(Person::getSalary)
                            .average()
                            .orElse(0);
                    System.out.printf("%s: %.2f%n", dept, avg);
                });

        // 6. Mensen met hobby fotografie
        System.out.println("\n=== Fotografen ===\n");
        SmartList<Person> photographers = SmartList.of(people)
                .filter(p -> p.getHobbies().contains("Photography"));
        photographers.toList().forEach(System.out::println);

        // 7. Top 3 hoogste salarissen
        System.out.println("\n=== Top 3 salarissen ===\n");
        SmartList<Person> topEarners = SmartList.of(people)
                .sorted((p1, p2) -> Double.compare(p2.getSalary(), p1.getSalary()))
                .limit(3);
        topEarners.toList().forEach(System.out::println);

        // 8. Getrouwde mensen in Amsterdam
        System.out.println("\n=== Getrouwd in Amsterdam ===\n");
        SmartList<Person> marriedInAmsterdam = SmartList.of(people)
                .filter(p -> p.isMarried() && "Amsterdam".equals(p.getCity()));
        marriedInAmsterdam.toList().forEach(System.out::println);

        // 9. Multi-sortering (afdeling, dan salaris)
        System.out.println("\n=== Gesorteerd op afdeling en salaris ===\n");
        SmartList<Person> multiSorted = SmartList.of(people)
                .sorted(Comparator
                        .comparing(Person::getDepartment)
                        .thenComparingDouble(Person::getSalary)
                );
        multiSorted.toList().forEach(System.out::println);

        // 10. Stad-statistieken
        System.out.println("\n=== Statistieken per stad ===\n");
        SmartList.of(people)
                .groupBy(Person::getCity)
                .toMap()
                .forEach((city, residents) -> {
                    System.out.println("Stad: " + city);
                    System.out.println("  Aantal: " + residents.size());
                    System.out.printf("  Gem. leeftijd: %.1f%n", residents.stream()
                            .mapToInt(Person::getAge)
                            .average().orElse(0));
                    System.out.printf("  Gem. salaris: %.0f%n", residents.stream()
                            .mapToDouble(Person::getSalary)
                            .average().orElse(0));
                    System.out.println("  Afdelingen: " + residents.stream()
                            .map(Person::getDepartment)
                            .distinct()
                            .toList());
                    System.out.println();
                });

        // 11. Alle unieke hobbies
        System.out.println("\n=== Alle unieke hobbies ===\n");
        SmartList<String> allHobbies = SmartList.of(people)
                .flatMap(p -> p.getHobbies().stream())
                .distinct()
                .sorted();
        allHobbies.toList().forEach(System.out::println);

        //BONUS: Complex voorbeeld met filtering, sortering en grouping
        System.out.println("\n=== Complex voorbeeld ===\n");
        Map<String, List<String>> result = SmartList.of(people)
                .filter(p -> p.getAge() > 25)
                .sortBy(Person::getSalary)
                .groupBy(Person::getDepartment)
                .having(group -> group.size() > 2)
                .limit(3)
                .map(Person::getName)
                .toMap();
        System.out.println(result);
    }
}