import validator.ValidationResult;
import validator.Validator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User("invalid-email", 15, null));
        users.add(new User("user@example.com", 19, "Jan"));

        users.forEach(user -> {
            ValidationResult result = Validator.validate(user);
            System.out.println("Class valid: " + result.isValid());
            System.out.println(result);
        });
    }
}
