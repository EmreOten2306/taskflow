package tech.ekya.taskflow.task;

import org.springframework.data.jpa.domain.Specification;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;

import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }
    public static Specification<Task> hasAssigneeId(Long assigneeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
    }
    public static Specification<Task> dueBefore(LocalDateTime dueDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("dueDate"), dueDate);

    }
    public static Specification<Task> search(String search) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + search + "%"),
                        criteriaBuilder.like(root.get("description"), "%" + search + "%")
                );
    }
}
