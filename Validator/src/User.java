import annotations.Email;
import annotations.NotNull;
import annotations.Range;

public class User {
    @NotNull
    @Email
    private String email;

    @Range(min = 18, max = 120)
    private int age;

    @NotNull
    private String name;

    public User(String email, int age, String name) {
        this.email = email;
        this.age = age;
        this.name = name;
    }
}
