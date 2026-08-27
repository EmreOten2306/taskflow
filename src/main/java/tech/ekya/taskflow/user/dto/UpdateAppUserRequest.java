package tech.ekya.taskflow.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.ekya.taskflow.user.Role;

public record UpdateAppUserRequest(
        @NotBlank
        @Size(max = 50)
        String fullName,

        @NotBlank
        @Size(min = 10, max = 30)
        String password,

        @NotBlank
        @Email
        String email,

        @NotNull
        Role role
) {
}
