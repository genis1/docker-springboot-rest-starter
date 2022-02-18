package genis.learning.docker;

import genis.learning.docker.exception.IllegalUserInputException;
import genis.learning.docker.service.IntegerCalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DockerTestApplicationTests {

	private final IntegerCalculatorService calculator;

	public DockerTestApplicationTests() {
		calculator = new IntegerCalculatorService();
	}


	@Test
	void contextLoads() {
	}

	@Test
	@DisabledOnJre(JRE.JAVA_17)
	@DisplayName("Should be running on java 17.")
	void failIfNotRunningOnJava17() {
		fail(() -> "Running on " + System.getProperty("java.version"));
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
