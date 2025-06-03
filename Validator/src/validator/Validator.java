package validator;

import annotations.Email;
import annotations.NotNull;
import annotations.Range;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static ValidationResult validate(Object obj) {
        ValidationResult result = new ValidationResult();

        if (obj == null) return result;
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);

                validateNotNull(field, value, result);
                validateEmail(field, value, result);
                validateRange(field, value, result);

            } catch (IllegalAccessException e) {
                e.getStackTrace();
            }
        }

        return result;
    }

    private static void validateNotNull(Field field, Object value, ValidationResult result) {
        if (field.isAnnotationPresent(NotNull.class)) {
            if (value == null) {
                result.addError(createValidationError(
                        field,
                        null,
                        field.getName() + " cannot be null!",
                        field.getAnnotation(NotNull.class)
                ));
            }
        }
    }

    private static void validateEmail(Field field, Object value, ValidationResult result) {
        if (field.isAnnotationPresent(Email.class)) {
            if (value instanceof String email && !EMAIL_PATTERN.matcher(email).matches()) {
                result.addError(createValidationError(
                        field,
                        value,
                        field.getName() + " is not a valid email address.",
                        field.getAnnotation(Email.class)
                ));
            }
        }
    }

    private static void validateRange(Field field, Object value, ValidationResult result) {
        if (field.isAnnotationPresent(Range.class)) {
            if (value instanceof Integer intValue) {
                Range range = field.getAnnotation(Range.class);
                if (intValue < range.min() || intValue > range.max()) {
                    result.addError(createValidationError(
                            field,
                            value,
                            field.getName() + " needs to be between " + range.min() + " and " + range.max(),
                            range
                    ));
                }
            }
        }
    }

    private static ValidationError createValidationError(
            Field field,
            Object value,
            String message,
            Object annotation) {
        return new ValidationError(
                field.getName(),
                value,
                message,
                String.valueOf(annotation)
        );
    }
}