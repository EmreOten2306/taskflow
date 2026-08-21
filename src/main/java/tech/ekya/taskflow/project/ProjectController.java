package tech.ekya.taskflow.project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.project.dto.CreateProjectRequest;
import tech.ekya.taskflow.project.dto.ProjectResponse;
import tech.ekya.taskflow.project.dto.UpdateProjectRequest;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectMapper projectMapper;
    private final ProjectService projectService;
    public ProjectController (ProjectService projectService, ProjectMapper projectMapper){
        this.projectService=projectService;
        this.projectMapper=projectMapper;
    }



    @PostMapping
    public ProjectResponse createProject(@RequestBody CreateProjectRequest request){
        Project project = projectMapper.toEntity(request);
        Project savedProject = projectService.createProject(project);
        return projectMapper.toResponse(savedProject);
    }


    @GetMapping
    public List<ProjectResponse> getAllProjects(
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) String search
    ) {
        return projectService.getAllProjects(status, ownerId, search)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }


    @GetMapping ("/{id}")
    public ProjectResponse getProjectFindById(@PathVariable Long id){
        return projectMapper.toResponse(projectService.getProjectById(id));
    }


    @PutMapping ("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id , @RequestBody UpdateProjectRequest request){
        Project existingProject = projectService.updateProject(id,request);
            return projectMapper.toResponse(existingProject);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }


    @PatchMapping("/{id}/status")
            public ProjectResponse patchProject(@PathVariable Long id,
                                        @RequestBody  ProjectStatus status){
       return projectMapper.toResponse(projectService.updateProjectStatus(id,status));

    }
}

