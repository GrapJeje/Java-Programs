package validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationResult {
    List<ValidationError> results = new ArrayList<>();

    public void addError(ValidationError error) {
        results.add(error);
    }

    public boolean isValid() {
        return results.isEmpty();
    }

    public List<ValidationError> getErrorsForField(String field) {
        return results.stream()
                .filter(error -> error.getField().equals(field))
                .collect(Collectors.toList());
    }

    public boolean hasErrorsForField(String field) {
        return results.stream().anyMatch(error -> error.getField().equals(field));
    }

    public ValidationError getFirstError() {
        return results.get(0);
    }

    @Override
    public String toString() {
        if (!this.isValid()) {
            StringBuilder builder = new StringBuilder();
            builder.append("ValidationResult: FAILED with ")
                    .append(results.size()).append(" errors:\n");
            results.forEach(builder::append);
            return builder.toString();
        } else return "ValidationResult: SUCCES";
    }
}
