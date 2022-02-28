package genis.learning.docker.common.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HandledExceptionMessageVo {
	private String message;
	private String code;
}
