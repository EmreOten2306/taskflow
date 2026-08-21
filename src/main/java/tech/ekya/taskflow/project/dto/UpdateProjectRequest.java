package tech.ekya.taskflow.project.dto;

import jakarta.validation.constraints.Size;
import tech.ekya.taskflow.project.ProjectStatus;

public record UpdateProjectRequest(
        @Size(max = 150)
        String name,

        @Size(max = 2000)
        String description,

         ProjectStatus status
) {

}
