package tech.ekya.taskflow.exception;

public record FieldErrorDetail(

        String field,
        String message
) {
}
