package tech.ekya.taskflow.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "jwt.secret=test-secret-key-test-secret-key-test-secret-key-12345678")
@ActiveProfiles("test")
class TaskflowApplicationTests {

	@Test
	void contextLoads() {
	}

}
