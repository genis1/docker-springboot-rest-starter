package genis.learning.docker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static genis.learning.docker.controller.HelloWorldController.HELLO_WORLD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Example integration test testing controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class HelloWorldTest {


	private static final String MESSAGE = "message";
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testHelloWorld() throws Exception {
		mockMvc.perform(get("/test"))
				.andExpect(status().isOk())
				.andExpect(content().string(HELLO_WORLD));
	}

	@Test
	void echo() throws Exception {
		mockMvc.perform(get("/test/echo/" + MESSAGE))
				.andExpect(status().isOk())
				.andExpect(content().string(MESSAGE));
	}
}
