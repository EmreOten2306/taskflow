package tech.ekya.taskflow.project.dto;

import jakarta.validation.constraints.Email;

public record UserSummary(

        String fullName,
        @Email
        String email

) {
}
