package tech.ekya.taskflow.project;
import org.springframework.stereotype.Component;
import tech.ekya.taskflow.project.dto.CreateProjectRequest;
import tech.ekya.taskflow.project.dto.ProjectResponse;
import tech.ekya.taskflow.project.dto.UpdateProjectRequest;
import tech.ekya.taskflow.user.dto.UserSummary;
import tech.ekya.taskflow.user.AppUser;

@Component
public class ProjectMapper {

            ///CREATE REQUEST
    public Project toEntity(CreateProjectRequest request) {
        Project project = new Project();

        project.setCode(request.code());
        project.setName(request.name());
        project.setDescription(request.description());
        return  project;
    }
            ///UPDATE REQUEST
    public void updateEntity(UpdateProjectRequest request, Project project) {

        project.setName(request.name());
        project.setDescription(request.description());
        project.setStatus(request.status());
    }
            /// PROJECT RESPONSE
            public ProjectResponse toResponse(Project project, int taskCount) {
                return new ProjectResponse(
                        project.getId(),
                        project.getCode(),
                        project.getName(),
                        project.getDescription(),
                        project.getStatus(),
                        toUserSummary(project.getOwner()),
                        taskCount,
                        project.getCreatedAt()
                );
            }
    private UserSummary toUserSummary(AppUser user) {

        return new UserSummary(
                user.getId(),
                user.getFullName(),
                user.getEmail()
        );
    }


}