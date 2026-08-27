package tech.ekya.taskflow.label.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLabelRequest(

        @NotBlank
        @Size(max = 50)
        String name
) {
}