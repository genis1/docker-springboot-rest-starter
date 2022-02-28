package genis.learning.docker.versioning;

import genis.learning.docker.common.pagination.PageRequest;
import genis.learning.docker.common.pagination.PaginationExceptionMessage;
import genis.learning.docker.versioning.vo.VersionDataVo;
import genis.learning.docker.versioning.vo.VersionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static genis.learning.docker.versioning.VersionService.*;

@RestController
@RequestMapping("version")
@Tags(value = {
		@Tag(name = "Version", description = "Version CRUD.")})
public class VersionController {

	private final VersionService service;

	public VersionController(VersionService service) {
		this.service = service;
	}

	@PostMapping()
	@Operation(
			summary = "Creates a new version.",
			description = "Inserts a new version into the DB.",
			operationId = "createVersion",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									schema = @Schema(implementation = VersionVo.class)
							))
			}
	)
	public VersionVo createVersion(@RequestBody VersionDataVo versionDataVo) {
		return service.create(versionDataVo);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Reads a versions.",
			description = "Finds a version by its id and returns its value, fails if it doesn't exist.",
			operationId = "readVersion",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									schema = @Schema(implementation = VersionVo.class)
							)),
					@ApiResponse(responseCode = "422", description = VERSION_ID_DOES_NOT_EXIST)
			}
	)
	public VersionVo readVersion(@Parameter(description = "Id of the version being read.")
								   @PathVariable() Integer id) {
		return service.read(id);
	}

	@GetMapping()
	@Operation(
			summary = "Reads versions paginated and filtered.",
			description = "Reads versions paginated and filtered, returns an empty result if nothing was found. It will search for exact matches of the informed fields.",
			operationId = "readVersions",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									schema = @Schema(implementation = PageImplVersion.class)
							)),
					@ApiResponse(responseCode = "422", description = VERSION_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY
							+ " " + PaginationExceptionMessage.PAGE_SIZE_MUST_NOT_BE_LESS_THAN_ONE)
			}
	)
	public Page<VersionVo> readVersions(@Parameter(description = "Version data used as a filter.")
												  VersionDataVo versionDataVo,
										  @Parameter(description = "Pagination data to use.")
												  PageRequest pageRequest) {
		return service.read(versionDataVo, pageRequest);
	}


	@PutMapping("/{id}")
	@Operation(
			summary = "Updates a version",
			description = "Updates an already existing version in the DB whose id is known.",
			operationId = "updateVersion",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful.",
							content = @Content(
									schema = @Schema(implementation = VersionVo.class)
							)),
					@ApiResponse(responseCode = "422", description = VERSION_ID_DOES_NOT_EXIST)
			}
	)
	ResponseEntity<VersionVo> updateVersion(
			@Parameter(description = "Id of the version being updated.")
			@PathVariable() Integer id,
			@Parameter(description = "Version data that wants to be updated.")
			@RequestBody VersionDataVo versionDataVo) {
		return ResponseEntity.ok(service.update(id, versionDataVo));
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Deletes a version",
			description = "Deletes a version from the DB whose id is known.",
			operationId = "deleteVersion",
			responses = {
					@ApiResponse(responseCode = "200", description = "Request successful."),
					@ApiResponse(responseCode = "422", description = VERSION_ID_DOES_NOT_EXIST)
			}
	)
	ResponseEntity<Void> deleteVersion(
			@Parameter(description = "Id of the version being updated.")
			@PathVariable() Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	/**
	 * This class is only used to define the swagger schema annotation.
	 */
	private abstract static class PageImplVersion extends PageImpl<VersionVo> {
		public PageImplVersion(List<VersionVo> content, Pageable pageable, long total) {
			super(content, pageable, total);
		}
	}
}
