package genis.learning.docker.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(IllegalUserInputException.class)
	public ResponseEntity<HandledExceptionMessageVo> handleException(IllegalUserInputException e) {
		return ResponseEntity
				.unprocessableEntity()
				.body(HandledExceptionMessageVo.builder()
						.code(IllegalUserInputException.class.getSimpleName())
						.message(e.getMessage())
						.build());
	}
}
