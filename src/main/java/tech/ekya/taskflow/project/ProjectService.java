package tech.ekya.taskflow.project;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.user.AppUserRepository;

import java.util.List;


@Transactional
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;

    public ProjectService(ProjectRepository projectRepository, AppUserRepository appUserRepository) {
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;


    }

    /// CREATE
    public Project createProject(Project project) {
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
                .orElseThrow();
    }


    /// UPDATE PROJECT STATUS

    public Project updateProjectStatus(Long id , ProjectStatus status){
        Project existingProject=projectRepository.findById(id)
                .orElseThrow();
        existingProject.setStatus(status);
        return projectRepository.save(existingProject);
    }

    /// UPDATE
    public Project updateProject(Long id, Project project) {

        Project existingProject = projectRepository.findById(id)
                .orElseThrow();
        existingProject.setCode(project.getCode());
        existingProject.setOwner(project.getOwner());
        existingProject.setDescription(project.getDescription());
        existingProject.setName(project.getName());
        existingProject.setStatus(project.getStatus());

        return projectRepository.save(existingProject);
    }
    /// DELETE
    public void  deleteProject(Long id){
        projectRepository.deleteById(id);


    }




}
