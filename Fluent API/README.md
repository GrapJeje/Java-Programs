# Fluent API

A fluent interface for manipulating collections with both list and map-like operations.
SmartList provides a chainable API for filtering, transforming, grouping, and aggregating data.

## Voorbeelden Fluent API

### 1. Filteren op leeftijd (<30 jaar)
```java
SmartList<Person> youngPeople = SmartList.of(people)
    .filter(p -> p.getAge() < 30);
youngPeople.toList().forEach(System.out::println);
```

### 2. Alle namen
```java
SmartList<String> names = SmartList.of(people)
    .select(Person::getName);
names.toList().forEach(System.out::println);
```

### 3. Sorteren op leeftijd
```java
SmartList<Person> sortedByAge = SmartList.of(people)
    .sorted(Comparator.comparingInt(Person::getAge));
sortedByAge.toList().forEach(System.out::println);
```

### 4. Unieke afdelingen vinden
```java
SmartList<String> departments = SmartList.of(people)
    .select(Person::getDepartment)
    .distinct();
departments.toList().forEach(System.out::println);
```

### 5. Gemiddeld salaris per afdeling
```java
SmartList.of(people)
    .groupBy(Person::getDepartment)
    .toMap()
    .forEach((dept, employees) -> {
        double avg = employees.stream()
            .mapToDouble(Person::getSalary)
            .average()
            .orElse(0);
        System.out.printf("%s: %.2f%n", dept, avg);
    });
```

### 6. Mensen met hobby fotografie
```java
SmartList<Person> photographers = SmartList.of(people)
    .filter(p -> p.getHobbies().contains("Photography"));
photographers.toList().forEach(System.out::println);
```

### 7. Top 3 hoogste salarissen
```java
SmartList<Person> topEarners = SmartList.of(people)
    .sorted((p1, p2) -> Double.compare(p2.getSalary(), p1.getSalary()))
    .limit(3);
topEarners.toList().forEach(System.out::println);
```

### 8. Getrouwde mensen in Amsterdam
```java
SmartList<Person> marriedInAmsterdam = SmartList.of(people)
    .filter(p -> p.isMarried() && "Amsterdam".equals(p.getCity()));
marriedInAmsterdam.toList().forEach(System.out::println);
```

### 9. Multi-sortering (afdeling, dan salaris)
```java
SmartList<Person> multiSorted = SmartList.of(people)
    .sorted(Comparator
        .comparing(Person::getDepartment)
        .thenComparingDouble(Person::getSalary)
    );
multiSorted.toList().forEach(System.out::println);
```

### 10. Gedetailleerde statistieken per stad
```java
SmartList.of(people)
    .groupBy(Person::getCity)
    .toMap()
    .forEach((city, residents) -> {
        System.out.println("Stad: " + city);
        System.out.println("  Aantal: " + residents.size());
        System.out.printf("  Gem. leeftijd: %.1f%n", 
            residents.stream().mapToInt(Person::getAge).average().orElse(0));
        System.out.printf("  Gem. salaris: %.0f%n", 
            residents.stream().mapToDouble(Person::getSalary).average().orElse(0));
        System.out.println("  Afdelingen: " + 
            residents.stream().map(Person::getDepartment).distinct().toList());
    });
```

### 11. Alle unieke hobbies
```java
SmartList<String> allHobbies = SmartList.of(people)
    .flatMap(p -> p.getHobbies().stream())
    .distinct()
    .sorted();
allHobbies.toList().forEach(System.out::println);
```

### Bonus: Complex voorbeeld met filtering, sortering en grouping
```java
Map<String, List<String>> result = SmartList.of(people)
    .filter(p -> p.getAge() > 25)           // Alleen 25+
    .sortBy(Person::getSalary)              // Sorteer op salaris
    .groupBy(Person::getDepartment)         // Groepeer per afdeling
    .having(group -> group.size() > 2)      // Alleen afdelingen > 2 mensen
    .limit(3)                               // Max 3 per afdeling
    .map(Person::getName)                   // Alleen namen
    .toMap();                               // Naar Map
System.out.println(result);
```