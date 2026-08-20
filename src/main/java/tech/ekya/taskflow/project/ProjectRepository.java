package tech.ekya.taskflow.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByCode(String code);
    boolean existsByCode(String code);

    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByOwnerId(Long ownerId);


}
