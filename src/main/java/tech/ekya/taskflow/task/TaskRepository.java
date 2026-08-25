package tech.ekya.taskflow.task;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.ekya.taskflow.project.ProjectStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findByProjectId(Long projectId);



}
