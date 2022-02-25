package genis.learning.docker.controller;

import genis.learning.docker.exception.IllegalUserInputException;
import genis.learning.docker.service.CustomerService;
import genis.learning.docker.vo.CustomerDataVo;
import genis.learning.docker.vo.CustomerVo;
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

import static genis.learning.docker.service.CustomerService.CUSTOMER_ID_DOES_NOT_EXIST;
import static genis.learning.docker.service.CustomerService.CUSTOMER_NAME_CANNOT_BE_EMTPY;

@RestController
@RequestMapping("customer")
@Tags(value = {
		@Tag(name = "Customer", description = "Customer CRUD.")})
public class CustomerController {

	private static final String CUSTOMER_VO_EXAMPLE = "{\n" +
			"  \"id\": 23,\n" +
			"  \"name\": \"foo\"\n" +
			"}";
	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@PostMapping()
	@Operation(
			summary = "Creates a new customer.",
			description = "Inserts a new customer into the DB.",
			operationId = "createCustomer",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									examples = @ExampleObject(value = CUSTOMER_VO_EXAMPLE),
									schema = @Schema(implementation = CustomerVo.class)
							)),
					@ApiResponse(responseCode = "422", description = CUSTOMER_NAME_CANNOT_BE_EMTPY)
			}
	)
	public CustomerVo createCustomer(@RequestBody CustomerDataVo customerDataVo) {
		return service.create(customerDataVo);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Reads a customers.",
			description = "Finds a customer by its id and returns its value, fails if it doesn't exist.",
			operationId = "readCustomers",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									examples = @ExampleObject(value = CUSTOMER_VO_EXAMPLE),
									schema = @Schema(implementation = CustomerVo.class)
							)),
					@ApiResponse(responseCode = "422", description = CUSTOMER_ID_DOES_NOT_EXIST)
			}
	)
	public CustomerVo readCustomer(@Parameter(description = "Id of the customer being read.")
								   @PathVariable() Integer id) {
		return service.read(id)
				.orElseThrow(() -> new IllegalUserInputException(CUSTOMER_ID_DOES_NOT_EXIST + " Id used was: " + id));
	}


	@PutMapping("/{id}")
	@Operation(
			summary = "Updates a customer",
			description = "Updates an already existing customer in the DB whose id is known.",
			operationId = "updateCustomer",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									examples = @ExampleObject(value = CUSTOMER_VO_EXAMPLE),
									schema = @Schema(implementation = CustomerVo.class)
							)),
					@ApiResponse(responseCode = "422", description = CUSTOMER_NAME_CANNOT_BE_EMTPY + "\n" +
							CUSTOMER_ID_DOES_NOT_EXIST)
			}
	)
	ResponseEntity<CustomerVo> updateCustomer(
			@Parameter(description = "Id of the customer being updated.")
			@PathVariable() Integer id,
			@Parameter(description = "Customer data that wants to be updated.")
			@RequestBody CustomerDataVo customerDataVo) {
		return ResponseEntity.ok(service.update(id, customerDataVo));
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Deletes a customer",
			description = "Deletes a customer from the DB whose id is known.",
			operationId = "deleteCustomer",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful."),
					@ApiResponse(responseCode = "422", description = CUSTOMER_ID_DOES_NOT_EXIST)
			}
	)
	ResponseEntity<Void> deleteCustomer(
			@Parameter(description = "Id of the customer being updated.")
			@PathVariable() Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
