package tech.ekya.taskflow.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
