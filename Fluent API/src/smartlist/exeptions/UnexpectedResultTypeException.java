package smartlist.exeptions;

public class UnexpectedResultTypeException extends RuntimeException {
    public UnexpectedResultTypeException() {
    }

    public UnexpectedResultTypeException(String message) {
        super(message);
    }

    public UnexpectedResultTypeException(Throwable cause) {
        super(cause);
    }

    public UnexpectedResultTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
