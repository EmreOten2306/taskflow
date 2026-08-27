package tech.ekya.taskflow.user.dto;

import jakarta.validation.constraints.Email;

public record UserSummary(
        Long id,
        String fullName,
        @Email
        String email

) {
}
