package tech.ekya.taskflow.project;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.project.dto.UpdateProjectRequest;
import tech.ekya.taskflow.user.AppUserRepository;

import java.util.List;


@Transactional
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, AppUserRepository appUserRepository,ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
        this.projectMapper = projectMapper;
    }

    /// CREATE
    public Project createProject(Project project) {
        if (projectRepository.existsByCode(project.getCode())) {
            throw new DuplicateResourceException(
                    "Project code already exists: " + project.getCode()
            );
        }

        var owner = appUserRepository.findById(1L).orElseThrow();
        project.setOwner(owner);

        return projectRepository.save(project);
    }



    /// READ
    public List<Project> getAllProjects(ProjectStatus status, Long ownerId, String search) {
        List<Project> projects = projectRepository.findAll();
        // Status filtresi
        if (status != null) {
            projects = projects.stream()
                    .filter(project -> project.getStatus() == status)
                    .toList();
        }
        // Owner filtresi
        if (ownerId != null) {
            projects = projects.stream()
                    .filter(project -> project.getOwner().getId().equals(ownerId))
                    .toList();
        }

        // Search filtresi
        if (search != null && !search.isBlank()) {
            projects = projects.stream()
                    .filter(project ->
                            project.getName().toLowerCase().contains(search.toLowerCase())
                                    || project.getCode().toLowerCase().contains(search.toLowerCase())
                    )
                    .toList();
        }
        return projects;
    }

    /// READ BY ID
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + id
                ));    }

    /// UPDATE PROJECT STATUS

    public Project updateProjectStatus(Long id , ProjectStatus status){
        Project existingProject=projectRepository.findById(id)
                .orElseThrow();
        existingProject.setStatus(status);
        return projectRepository.save(existingProject);
    }

    /// UPDATE
    public Project updateProject(Long id, UpdateProjectRequest request) {

        Project existingProject = projectRepository.findById(id)
                .orElseThrow();

        projectMapper.updateEntity(request, existingProject);

        return projectRepository.save(existingProject);
    }
    /// DELETE
    public void  deleteProject(Long id){
        projectRepository.deleteById(id);


    }




}
