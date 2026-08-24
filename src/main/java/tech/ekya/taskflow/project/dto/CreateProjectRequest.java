package tech.ekya.taskflow.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
        @NotBlank
        @Size(max = 20)
        String code,

        @NotBlank
        @Size(max = 150)
        String name,

        @Size(max = 2000)
        String description,

        @Positive
        @NotNull
        Long ownerId
) {
}