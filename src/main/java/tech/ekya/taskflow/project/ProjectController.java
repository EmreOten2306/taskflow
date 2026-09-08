package tech.ekya.taskflow.project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.project.dto.CreateProjectRequest;
import tech.ekya.taskflow.project.dto.ProjectResponse;
import tech.ekya.taskflow.project.dto.UpdateProjectRequest;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(
        name = "Projects",
        description = "Project management operations"
)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /// CREATE
    @Operation(
            summary = "Create a project",
            description = "Creates a new project"
    )
    @ApiResponse(responseCode = "200", description = "Project created successfully")
    @ApiResponse(responseCode = "409", description = "Project already exists")
    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request
    ) {
        return projectService.createProject(request);
    }

    /// GET ALL
    @Operation(
            summary = "Get all projects",
            description = "Returns all projects with optional status, owner and search filters"
    )
    @ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
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
    @Operation(
            summary = "Get project by ID",
            description = "Returns a project by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Project retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id
    ) {
        return projectService.getProjectById(id);
    }

    /// UPDATE
    @Operation(
            summary = "Update a project",
            description = "Updates an existing project by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Project updated successfully")
    @ApiResponse(responseCode = "403", description = "User is not the project owner")
    @ApiResponse(responseCode = "404", description = "Project not found")
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
    @Operation(
            summary = "Update project status",
            description = "Updates the status of an existing project"
    )
    @ApiResponse(responseCode = "200", description = "Project status updated successfully")
    @ApiResponse(responseCode = "403", description = "User is not the project owner")
    @ApiResponse(responseCode = "404", description = "Project not found")
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
    @Operation(
            summary = "Delete a project",
            description = "Deletes a project by its ID. Only ADMIN users are authorized."
    )
    @ApiResponse(responseCode = "204", description = "Project deleted successfully")
    @ApiResponse(responseCode = "403", description = "User does not have ADMIN role")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
