package genis.learning.docker.tests;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Tags(value = {
		@Tag(name = "Testing", description = "Endpoints used to test integration with the application.")})
public class TestController {

	public static final String HELLO_WORLD = "Hello, world!";

	@GetMapping()
	@Operation(summary = "Returns \"Hello, world!\"",
			description = "Unsecured endpoint used to test connection.",
			operationId = "testHelloWorld")
	String helloWorld() {
		return HELLO_WORLD;
	}

	@GetMapping("echo/{message}")
	@Operation(
			summary = "Returns given message",
			description = "Unsecured endpoint used to test sending path variables.",
			operationId = "testEcho"
	)
	String echo(
			@Parameter(description = "Message to be returned.")
			@PathVariable String message) {
		return message;
	}
}
