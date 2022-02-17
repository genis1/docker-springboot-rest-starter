package genis.learning.docker.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HandledExceptionMessageVo {
	private String message;
	private String code;
}
