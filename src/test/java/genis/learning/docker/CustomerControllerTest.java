package genis.learning.docker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Example integration test testing controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CustomerControllerTest {


	private static final String MESSAGE = "message";
	@Autowired
	private MockMvc mockMvc;

	@Test
	void postCustomer() throws Exception {
		mockMvc.perform(post("/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"foo\"}"))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"name\":\"foo\"}"));
	}
}
