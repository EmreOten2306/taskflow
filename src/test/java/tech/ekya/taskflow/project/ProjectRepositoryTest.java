package tech.ekya.taskflow.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void testFindByCode() {
        AppUser admin = appUserRepository.findById(1L).orElseThrow();

        Project project = new Project();
        project.setCode("TEST-003");
        project.setOwner(admin);

        projectRepository.save(project);

        Project foundProject = projectRepository.findByCode("TEST-001");
        assertNotNull(foundProject);

        projectRepository.delete(project);

    }
    @Test
    void testExistsByCode() {

        AppUser admin = appUserRepository.findById(1L).orElseThrow();

        Project project = new Project();
        project.setCode("TEST-002");
        project.setOwner(admin);
        projectRepository.save(project);

        boolean exists = projectRepository.existsByCode("TEST-002");
        assertTrue(exists);
        projectRepository.delete(project);
    }

}