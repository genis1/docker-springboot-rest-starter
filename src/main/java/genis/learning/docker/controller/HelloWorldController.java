package genis.learning.docker.controller;

import genis.learning.docker.service.TestService;
import genis.learning.docker.vo.DivisionRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@Tags(value = {
		@Tag(name = "Testing", description = "Endpoints used to test integration with the application.")})
public class HelloWorldController {

	private final TestService service;

	public HelloWorldController(TestService service) {
		this.service = service;
	}

	@GetMapping()
	@Operation(summary = "Returns \"Hello, world!\"",
			description = "Unsecured endpoint used to test connection.",
			operationId = "testHelloWorld")
	String helloWorld() {
		return "Hello, world!";
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

	@PostMapping("division")
	@Operation(
			summary = "Computes division",
			description = "Computes the division specified in the request and returns the return as a double.",
			operationId = "testDivision",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful",
							content = @Content(
									examples = @ExampleObject(value = "3.5"),
									schema = @Schema(implementation = Float.class)
							)),
					@ApiResponse(responseCode = "422", description = TestService.DIVISOR_CANNOT_BE_0)
			}
	)
	ResponseEntity<Float> divide(
			@Parameter(description = "Division request item containing its inputs.")
			@RequestBody DivisionRequestVo divisionRequestVo) {
		return ResponseEntity.ok(service.divide(divisionRequestVo.getDividend(), divisionRequestVo.getDivisor()));
	}
}
