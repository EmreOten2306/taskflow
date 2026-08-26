package tech.ekya.taskflow.task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;


import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    Page<Task> findByProjectId(Long projectId, Pageable pageable);
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);
    Page<Task> findByDueDateBefore(LocalDateTime dueBefore, Pageable pageable);

    Page<Task> findByTitleContainingOrDescriptionContaining(
            String title,
            String description,
            Pageable pageable
    );


    List<Task> findByAssigneeId(Long userId);

}
