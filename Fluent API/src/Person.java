import java.util.Objects;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Person implements Comparable<Person> {
    private String name;
    private int age;
    private String city;
    private String department;
    private double salary;
    private String email;
    private Set<String> hobbies;
    private boolean married;
    private int children;
    private String educationLevel;
    private int yearsOfExperience;

    public Person(String name, int age, String city, String department, double salary, String email,
                  Set<String> hobbies, boolean married, int children, String educationLevel,
                  int yearsOfExperience) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.department = department;
        this.salary = salary;
        this.email = email;
        this.hobbies = hobbies;
        this.married = married;
        this.children = children;
        this.educationLevel = educationLevel;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getHobbies() {
        return hobbies;
    }

    public boolean isMarried() {
        return married;
    }

    public int getChildren() {
        return children;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHobbies(Set<String> hobbies) {
        this.hobbies = hobbies;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, city='%s', dept='%s', salary=%.0f, hobbies=%s, married=%b, children=%d, education='%s', exp=%d}",
                name, age, city, department, salary, hobbies, married, children, educationLevel, yearsOfExperience);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Double.compare(person.salary, salary) == 0 &&
                married == person.married &&
                children == person.children &&
                yearsOfExperience == person.yearsOfExperience &&
                Objects.equals(name, person.name) &&
                Objects.equals(email, person.email) &&
                Objects.equals(educationLevel, person.educationLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary, email, married, children, educationLevel, yearsOfExperience);
    }

    public static List<Person> createTestData() {
        return Arrays.asList(
                new Person("Alice", 28, "Amsterdam", "Engineering", 75000, "alice@company.com",
                        Set.of("Hiking", "Photography"), false, 0, "Master", 5),
                new Person("Bob", 35, "Rotterdam", "Engineering", 82000, "bob@company.com",
                        Set.of("Gaming", "Cooking"), true, 2, "Bachelor", 10),
                new Person("Charlie", 22, "Amsterdam", "Marketing", 45000, "charlie@company.com",
                        Set.of("Reading", "Writing"), false, 0, "Bachelor", 1),
                new Person("Diana", 31, "Utrecht", "Engineering", 78000, "diana@company.com",
                        Set.of("Swimming", "Cycling"), true, 1, "PhD", 7),
                new Person("Eve", 26, "Amsterdam", "Sales", 55000, "eve@company.com",
                        Set.of("Dancing", "Singing"), false, 0, "Bachelor", 3),
                new Person("Frank", 42, "Rotterdam", "Engineering", 95000, "frank@company.com",
                        Set.of("Chess", "Gardening"), true, 3, "Master", 15),
                new Person("Grace", 29, "Utrecht", "Marketing", 52000, "grace@company.com",
                        Set.of("Painting", "Yoga"), false, 0, "Master", 4),
                new Person("Henry", 33, "Amsterdam", "Sales", 68000, "henry@company.com",
                        Set.of("Football", "Movies"), true, 0, "Bachelor", 8),
                new Person("Ivy", 27, "Rotterdam", "Marketing", 48000, "ivy@company.com",
                        Set.of("Traveling", "Languages"), false, 0, "Bachelor", 2),
                new Person("Jack", 38, "Utrecht", "Engineering", 88000, "jack@company.com",
                        Set.of("DIY", "Fishing"), true, 2, "PhD", 12),
                new Person("Kate", 24, "Amsterdam", "Sales", 41000, "kate@company.com",
                        Set.of("Photography", "Blogging"), false, 0, "Bachelor", 1),
                new Person("Liam", 36, "Rotterdam", "Engineering", 91000, "liam@company.com",
                        Set.of("Running", "Meditation"), true, 1, "Master", 11),
                new Person("Mia", 30, "Utrecht", "Marketing", 58000, "mia@company.com",
                        Set.of("Cooking", "Wine tasting"), false, 0, "Master", 6),
                new Person("Noah", 25, "Amsterdam", "Engineering", 72000, "noah@company.com",
                        Set.of("Gaming", "Programming"), false, 0, "Bachelor", 3),
                new Person("Olivia", 32, "Rotterdam", "Sales", 63000, "olivia@company.com",
                        Set.of("Shopping", "Fashion"), true, 1, "Bachelor", 7)
        );
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
}