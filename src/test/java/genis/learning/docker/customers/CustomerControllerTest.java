package genis.learning.docker.customers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Example integration test testing controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {


	private static final String MESSAGE = "message";
	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	void postCustomer() throws Exception {
		mockMvc.perform(post("/customer")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"foo\"}"))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"name\":\"foo\"}"));
	}

	@Test
	@Order(2)
	void getCustomer() throws Exception {
		mockMvc.perform(get("/customer?name=foo&size=10"))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"content\":[{\"name\":\"foo\"}]}"));
	}

	@Test
	@Order(3)
	void deleteCustomer() throws Exception {
		mockMvc.perform(delete("/customer/1"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void getEmptyCustomer() throws Exception {
		mockMvc.perform(get("/customer/1"))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	@Order(5)
	void deleteEmptyCustomer() throws Exception {
		mockMvc.perform(delete("/customer/1"))
				.andExpect(status().isUnprocessableEntity());
	}
}
