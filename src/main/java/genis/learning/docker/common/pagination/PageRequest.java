package genis.learning.docker.common.pagination;

import genis.learning.docker.common.exception.IllegalUserInputException;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

@Data
public class PageRequest {

	private int page;
	private int size;
	private org.springframework.data.domain.Sort.Direction direction;
	private String[] properties;

	public org.springframework.data.domain.PageRequest build() {
		if (this.getSize() < 1) {
			throw new IllegalUserInputException(PaginationExceptionMessage.PAGE_SIZE_MUST_NOT_BE_LESS_THAN_ONE);
		}

		if (this.getProperties() == null) {
			return org.springframework.data.domain.PageRequest.of(page, size);
		} else {
			final Sort.Direction direction = ObjectUtils.isEmpty(this.getDirection()) ?
					Sort.Direction.ASC : this.getDirection();
			return org.springframework.data.domain.PageRequest.of(this.getPage(), this.getSize(), direction, this.getProperties());
		}
	}
}
