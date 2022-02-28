package genis.learning.docker.integercalculator;

import genis.learning.docker.common.exception.IllegalUserInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerCalculatorTest {

	private final IntegerCalculatorService calculator;

	public IntegerCalculatorTest() {
		calculator = new IntegerCalculatorService();
	}

	@ParameterizedTest(name = "Test {0}/{1}={2}")
	@CsvSource({"9,3,3", "4,2,2", "56,8,7"})
	void testDivision(Float dividend, Float divisor, Float quotioent) {
		final Float result = calculator.divide(dividend, divisor);
		assertEquals(result, quotioent);
	}

	@Test
	@DisplayName("Exception is thrown when dividing by 0.")
	void assertExceptionDivideByZero() {
		final IllegalUserInputException illegalUserInputException = assertThrows(IllegalUserInputException.class, () -> calculator.divide(2f, 0f));
		assertEquals(illegalUserInputException.getMessage(), IntegerCalculatorService.DIVISOR_CANNOT_BE_0);
	}
}
