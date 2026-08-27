package tech.ekya.taskflow.project;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByCode(String code);
    boolean existsByCode(String code);

    @EntityGraph(attributePaths = {"owner"})
    Optional<Project> findProjectWithOwnerById(Long id);

    @EntityGraph(attributePaths = {"owner"})
    List<Project> findAll();

    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByOwnerId(Long ownerId);


}
