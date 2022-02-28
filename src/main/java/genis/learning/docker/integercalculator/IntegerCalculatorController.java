package genis.learning.docker.integercalculator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculator")
@Tags(value = {
		@Tag(name = "Testing", description = "Endpoints used to test integration with the application.")})
public class IntegerCalculatorController {

	private final IntegerCalculatorService service;

	public IntegerCalculatorController(IntegerCalculatorService service) {
		this.service = service;
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
					@ApiResponse(responseCode = "422", description = IntegerCalculatorService.DIVISOR_CANNOT_BE_0)
			}
	)
	ResponseEntity<Float> divide(
			@Parameter(description = "Division request item containing its inputs.")
			@RequestBody DivisionRequestVo divisionRequestVo) {
		return ResponseEntity.ok(service.divide(divisionRequestVo.getDividend(), divisionRequestVo.getDivisor()));
	}
}
