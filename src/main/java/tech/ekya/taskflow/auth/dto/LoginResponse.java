package tech.ekya.taskflow.auth.dto;

public record LoginResponse(
        String accessToken,
        long expiresIn
) {
}
