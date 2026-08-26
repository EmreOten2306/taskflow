package tech.ekya.taskflow.task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.ekya.taskflow.task.taskenums.TaskStatus;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    Page<Task> findByProjectId(Long projectId, Pageable pageable);
    List<Task> findByAssigneeId(Long userId);

    boolean existsByAssigneeIdAndStatusNot(Long assigneeId, TaskStatus status);
}
