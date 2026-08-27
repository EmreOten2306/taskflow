package tech.ekya.taskflow.task;

import org.springframework.stereotype.Component;
import tech.ekya.taskflow.label.dto.LabelSummary;
import tech.ekya.taskflow.project.dto.ProjectSummary;
import tech.ekya.taskflow.task.dto.CreateTaskRequest;
import tech.ekya.taskflow.task.dto.TaskResponse;
import tech.ekya.taskflow.task.dto.UpdateTaskRequest;
import tech.ekya.taskflow.user.dto.UserSummary;

import java.util.List;

@Component
public class TaskMapper {

    public Task toEntity(CreateTaskRequest request) {
        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDueDate(request.dueDate());

        return task;
    }


    public void updateTaskEntity(
            UpdateTaskRequest request,
            Task existingTask
    ) {
        existingTask.setTitle(request.title());
        existingTask.setDescription(request.description());
        existingTask.setPriority(request.priority());
        existingTask.setDueDate(request.dueDate());
    }

    public TaskResponse toResponse(Task task) {

        ProjectSummary projectSummary = new ProjectSummary(
                task.getProject().getId(),
                task.getProject().getCode(),
                task.getProject().getName()
        );

        UserSummary assigneeSummary = null;

        if (task.getAssignee() != null) {
            assigneeSummary = new UserSummary(
                    task.getAssignee().getId(),
                    task.getAssignee().getFullName(),
                    task.getAssignee().getEmail()
            );
        }

        List<LabelSummary> labelSummaries = task.getLabels()
                .stream()
                .map(label -> new LabelSummary(
                        label.getId(),
                        label.getName()
                ))
                .toList();

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCompletedAt(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                projectSummary,
                assigneeSummary,
                labelSummaries
        );
    }
}