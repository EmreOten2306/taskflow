package tech.ekya.taskflow.auth.dto;

public record RegisterRequest(
        String fullName,
        String email,
        String password
) {
}
