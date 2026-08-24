package tech.ekya.taskflow.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.ekya.taskflow.project.ProjectStatus;

public record UpdateProjectRequest(
        @NotBlank
        @Size(max = 150)
        String name,

        @Size(max = 2000)
        String description,

         @NotNull
         ProjectStatus status
) {

}
