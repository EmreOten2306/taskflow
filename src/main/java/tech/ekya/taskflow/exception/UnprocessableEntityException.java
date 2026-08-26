package tech.ekya.taskflow.exception;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
