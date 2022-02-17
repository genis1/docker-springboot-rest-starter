package genis.learning.docker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Starter application",
				description = "Application created to use as a starter for a REST application with documentation and a database.",
				version = "Pre-Alpha",
				contact = @Contact(
						name = "Genís Guillem Mimó",
						email = "genisguillem@gmail.com")
		)
)
@SpringBootApplication
public class DockerTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerTestApplication.class, args);
	}

}
