package genis.learning.docker.integercalculator;

import genis.learning.docker.common.exception.IllegalUserInputException;
import org.springframework.stereotype.Service;

@Service
public class IntegerCalculatorService {

	public static final String DIVISOR_CANNOT_BE_0 = "Divisor cannot be 0";

	public Float divide(Float dividend, Float divisor) throws IllegalUserInputException {
		if (divisor == 0)
			throw new IllegalUserInputException(DIVISOR_CANNOT_BE_0);

		return dividend / divisor;
	}
}
