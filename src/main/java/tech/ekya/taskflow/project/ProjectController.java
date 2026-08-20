package tech.ekya.taskflow.project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController (ProjectService projectService){
        this.projectService=projectService;
    }
    @PostMapping
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Project> getAllProjects(
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) String search
    ) {
        return projectService.getAllProjects(status, ownerId, search);
    }
    @GetMapping ("/{id}")
    public Project getProjectFindById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }
    @PutMapping ("/{id}")
    public Project updateProject(@PathVariable Long id ,@RequestBody Project project){
        Project existingProject = projectService.updateProject(id,project);
            return existingProject;
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }
    @PatchMapping("/{id}/status")
            public Project patchProject(@PathVariable Long id,
                                        @RequestBody  ProjectStatus status){
       return  projectService.updateProjectStatus(id,status);

    }
}

