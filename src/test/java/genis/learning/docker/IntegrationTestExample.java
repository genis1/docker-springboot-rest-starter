package genis.learning.docker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Example of integration testing. This test loads spring context so is much slower to start.
 * Note that spring context is cached even between different test classes so other executions are gaster to load.
 */
@SpringBootTest
class IntegrationTestExample {

	@Test
	void contextLoads() {
	}

}
