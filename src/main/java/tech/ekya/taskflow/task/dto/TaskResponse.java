package tech.ekya.taskflow.task.dto;

import tech.ekya.taskflow.label.dto.LabelSummary;
import tech.ekya.taskflow.project.dto.ProjectSummary;
import tech.ekya.taskflow.user.dto.UserSummary;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ProjectSummary project,
        UserSummary assignee,
        List<LabelSummary> labels
) {
}
