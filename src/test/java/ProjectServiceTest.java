import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.project.Project;
import tech.ekya.taskflow.project.ProjectMapper;
import tech.ekya.taskflow.project.ProjectRepository;
import tech.ekya.taskflow.project.ProjectService;
import tech.ekya.taskflow.project.dto.CreateProjectRequest;
import tech.ekya.taskflow.project.dto.ProjectResponse;
import tech.ekya.taskflow.task.TaskRepository;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    ProjectService projectService;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    ProjectMapper projectMapper;

    @Mock
    TaskRepository taskRepository;

    @Test
    void createProject_shouldCreateProjectSuccessfully() {
        CreateProjectRequest request = new CreateProjectRequest(
                "TEST-01",
                "Test Project",
                "Test project description",
                1L
        );

        when(projectRepository.existsByCode(request.code()))
                .thenReturn(false);

        AppUser owner = new AppUser();
        owner.setFullName("Test User");
        owner.setEmail("test@test.com");

        when(appUserRepository.findById(request.ownerId()))
                .thenReturn(Optional.of(owner));

        Project project = new Project();
        project.setCode(request.code());
        project.setName(request.name());
        project.setDescription(request.description());
        project.setOwner(owner);

        when(projectMapper.toEntity(request))
                .thenReturn(project);

        when(projectRepository.save(project))
                .thenReturn(project);
        ProjectResponse projectResponse = new ProjectResponse(
                1L,
                "TEST-01",
                "Test Project",
                "Test project description",
                null,
                null,
                0,
                null
        );

        when(projectMapper.toResponse(project, 0))
                .thenReturn(projectResponse);

        ProjectResponse response = projectService.createProject(request);

        assertEquals(projectResponse, response);

        verify(projectRepository).save(project);
        verify(projectRepository).existsByCode(request.code());

    }

    @Test
    void createProject_shouldThrowExceptionWhenCodeAlreadyExists() {

        CreateProjectRequest request = new CreateProjectRequest(
                "TEST-01",
                "Test Project",
                "Test project description",
                1L
        );

        when(projectRepository.existsByCode(request.code()))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> projectService.createProject(request)
        );

        verify(projectRepository).existsByCode(request.code());
    }


}
