package tech.ekya.taskflow.task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.ekya.taskflow.task.taskenums.TaskStatus;
import org.springframework.data.jpa.repository.EntityGraph;


import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
   @Query("""
SELECT t
FROM Task t
JOIN FETCH t.project
LEFT JOIN FETCH t.assignee
WHERE t.dueDate < CURRENT_TIMESTAMP 
 """)
   List<Task> findOverdueTasks();


   @Query(value = """
   SELECT status, COUNT(*)
FROM task
WHERE project_id = :projectId
GROUP BY status;
""", nativeQuery = true)
   List<TaskStatusCountProjection> countByStatusForProject(
    @Param("projectId") Long projectId
);


    @EntityGraph(attributePaths = {"project", "assignee", "labels"})
    Page<Task> findByProjectId(Long projectId, Pageable pageable);

    boolean existsByProjectId(Long projectId);

    List<Task> findByAssigneeId(Long userId);

    boolean existsByProjectIdAndTitle(Long projectId, String title);
    boolean existsByAssigneeIdAndStatusNot(Long assigneeId,
                                           TaskStatus status);

    boolean existsByProjectIdAndTitleAndIdNot(
            Long projectId,
            String title,
            Long taskId
    );

    long countByProjectId(Long projectId);

    @EntityGraph(attributePaths = {"project", "assignee","labels"})
    Page<Task> findAll(Specification<Task> spec,
                       Pageable pageable);

    @EntityGraph(attributePaths = {"project", "assignee", "labels"})
    Optional<Task> findTaskWithDetailsById(Long id);

}
