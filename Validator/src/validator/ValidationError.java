package validator;

public class ValidationError {
    private final String field;
    private final Object value;
    private final String message;

    public ValidationError(String field, Object value, String message, String annotation) {
        this.field = field;
        this.value = value;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "- " + this.getField() + ": \"" + this.getValue() + "\" -> " + this.getMessage() + "\n";
    }
}
