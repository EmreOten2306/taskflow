package tech.ekya.taskflow.task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.ekya.taskflow.task.taskenums.TaskStatus;
import org.springframework.data.jpa.repository.EntityGraph;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    Page<Task> findByProjectId(Long projectId, Pageable pageable);
    List<Task> findByAssigneeId(Long userId);

    boolean existsByProjectIdAndTitle(Long projectId, String title);
    boolean existsByAssigneeIdAndStatusNot(Long assigneeId, TaskStatus status);

    boolean existsByProjectIdAndTitleAndIdNot(
            Long projectId,
            String title,
            Long taskId
    );

    @EntityGraph(attributePaths = {"project", "assignee"})
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);


}
