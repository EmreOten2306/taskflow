package tech.ekya.taskflow.project;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.project.dto.CreateProjectRequest;
import tech.ekya.taskflow.project.dto.ProjectResponse;
import tech.ekya.taskflow.project.dto.UpdateProjectRequest;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /// CREATE
    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request
    ) {

        return projectService.createProject(request);
    }

    /// GET ALL
    @GetMapping
    public List<ProjectResponse> getAllProjects(
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) String search
    ) {

        return projectService.getAllProjects(
                status,
                ownerId,
                search
        );
    }

    /// GET BY ID
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id
    ) {

        return projectService.getProjectById(id);
    }

    /// UPDATE
    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request
    ) {

        return projectService.updateProject(
                id,
                request
        );
    }

    /// UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ProjectResponse updateProjectStatus(
            @PathVariable Long id,
            @RequestBody ProjectStatus status
    ) {

        return projectService.updateProjectStatus(
                id,
                status
        );
    }

    /// DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @PathVariable Long id
    ) {

        projectService.deleteProject(id);
    }
}