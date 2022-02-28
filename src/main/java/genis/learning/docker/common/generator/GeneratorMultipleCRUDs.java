package genis.learning.docker.common.generator;

import java.io.IOException;
import java.util.List;

public class GeneratorMultipleCRUDs {

	public static void main(String[] args) throws IOException {
		generate("patient", "patients", List.of("name"));
		generate("appliedTreatment", "applied.treatments", List.of("name", "patient_id"));

	}

	private static void generate(String generatedName, String generatedPackageName, List<String> generatedAttributes) throws IOException {
		(new GenerateCRUD(generatedName, generatedPackageName, generatedAttributes)).generate();
	}
}
