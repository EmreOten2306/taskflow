package tech.ekya.taskflow.project;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.project.dto.CreateProjectRequest;
import tech.ekya.taskflow.project.dto.ProjectResponse;
import tech.ekya.taskflow.project.dto.UpdateProjectRequest;
import tech.ekya.taskflow.task.TaskRepository;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;

import java.util.List;

@Transactional
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            AppUserRepository appUserRepository,
            ProjectMapper projectMapper,
            TaskRepository taskRepository
    ) {
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
        this.projectMapper = projectMapper;
        this.taskRepository = taskRepository;
    }

    /// CREATE
    public ProjectResponse createProject(CreateProjectRequest request) {

        if (projectRepository.existsByCode(request.code())) {
            throw new DuplicateResourceException(
                    "Project code already exists: " + request.code()
            );
        }

        AppUser owner = appUserRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Owner not found with id: " + request.ownerId()
                ));

        Project project = projectMapper.toEntity(request);

        project.setOwner(owner);

        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponse(savedProject, 0);
    }

    /// GET ALL
    public List<ProjectResponse> getAllProjects(
            ProjectStatus status,
            Long ownerId,
            String search
    ) {

        List<Project> projects = projectRepository.findAll();

        if (status != null) {
            projects = projects.stream()
                    .filter(project -> project.getStatus() == status)
                    .toList();
        }

        if (ownerId != null) {
            projects = projects.stream()
                    .filter(project ->
                            project.getOwner().getId().equals(ownerId)
                    )
                    .toList();
        }

        if (search != null && !search.isBlank()) {
            String searchText = search.toLowerCase();

            projects = projects.stream()
                    .filter(project ->
                            project.getName().toLowerCase().contains(searchText)
                                    || project.getCode().toLowerCase().contains(searchText)
                    )
                    .toList();
        }

        return projects.stream()
                .map(project -> {

                    int taskCount = (int) taskRepository.countByProjectId(
                            project.getId()
                    );

                    return projectMapper.toResponse(
                            project,
                            taskCount
                    );
                })
                .toList();
    }

    /// GET BY ID
    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository
                .findProjectWithOwnerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + id
                ));

        int taskCount = (int) taskRepository.countByProjectId(id);

        return projectMapper.toResponse(
                project,
                taskCount
        );
    }

    /// UPDATE STATUS
    public ProjectResponse updateProjectStatus(
            Long id,
            ProjectStatus status
    ) {

        Project existingProject = projectRepository
                .findProjectWithOwnerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + id
                ));

        existingProject.setStatus(status);

        Project savedProject = projectRepository.save(existingProject);

        int taskCount = (int) taskRepository.countByProjectId(id);

        return projectMapper.toResponse(
                savedProject,
                taskCount
        );
    }

    /// UPDATE
    public ProjectResponse updateProject(
            Long id,
            UpdateProjectRequest request
    ) {

        Project existingProject = projectRepository
                .findProjectWithOwnerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + id
                ));

        projectMapper.updateEntity(
                request,
                existingProject
        );

        Project savedProject = projectRepository.save(existingProject);

        int taskCount = (int) taskRepository.countByProjectId(id);

        return projectMapper.toResponse(
                savedProject,
                taskCount
        );
    }

    /// DELETE
    public void deleteProject(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + id
                ));
        if (taskRepository.existsByProjectId(id)) {
            throw new DuplicateResourceException(
                    "Project cannot be deleted because it has tasks"
            );
        }



        projectRepository.delete(project);
    }
}