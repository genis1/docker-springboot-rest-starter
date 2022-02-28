package genis.learning.docker.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Example of unit testing. Note that no spring context is loaded. This test is much faster.
 */
public class UnitTestExample {

	@Test
	@DisabledOnJre(JRE.JAVA_17)
	@DisplayName("Should be running on java 17.")
	void failIfNotRunningOnJava17() {
		fail(() -> "Running on " + System.getProperty("java.version"));
	}
}
