package tech.ekya.taskflow.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tech.ekya.taskflow.task.taskenums.TaskPriority;

import java.time.LocalDateTime;

public record UpdateTaskRequest(
        @NotBlank
        @Size(max = 100)
        String title,

        @Size(max = 1000)
        String description,

        TaskPriority priority,

        LocalDateTime dueDate
) {
}