package genis.learning.docker.common.generator;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.buf.StringUtils.join;

/**
 * name is a reserved name.
 */
@Log4j2
public class GenerateCRUD {
	private final static String templateName = "customer";
	private final static String templatePackageName = "customers";
	private final static String generatedName = "version";
	private final static String generatedPackageName = "versioning";

	private final static String templateLocation = "src/main/java/genis/learning/docker/" + templatePackageName;
	private final static String generatedLocation = "src/main/java/genis/learning/docker/" + generatedPackageName;
	private final static String templatePackageRoot = "genis.learning.docker." + templatePackageName;
	private final static String generatedPackageRoot = "genis.learning.docker." + generatedPackageName;

	private static final String TemplateName = capitalizeFirstLetter(templateName);
	private static final String TEMPLATE_NAME = templateName.toUpperCase();
	private static final String GeneratedName = capitalizeFirstLetter(generatedName);
	private static final String GENERATED_NAME = generatedName.toUpperCase();

	private static final List<String> generatedAttributes = List.of("name"); //Reversed order, name is mandatory.

	private static final String script = "-- changeset genis.guillem.mimo:create-table-customers\n" +
			"CREATE TABLE `customers`\n" +
			"(\n" +
			"    `id`   INT          NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(255) NULL,\n" +
			"    PRIMARY KEY (`id`)\n" +
			");\n";


	public static void main(String[] args) throws IOException {

		final Path templatePath = Path.of(templateLocation);
		Files.walk(templatePath)
				.filter(Files::isRegularFile)
				.forEach(GenerateCRUD::copyPath);

		final List<String> splittedScript = Arrays.stream(script.split("\n")).collect(Collectors.toList());
		final List<String> newSplittedScript = transformScriptClasses(splittedScript, generatedAttributes);
		Files.write(
				Paths.get("src/main/resources/db/changelog/base_changelog.sql"),
				join(newSplittedScript, '\n').getBytes(),
				StandardOpenOption.APPEND);
	}

	private static void copyPath(Path path) {
		try {
			final List<String> lines = Files.readAllLines(path);
			final List<String> transformedLines = lines.stream().map(GenerateCRUD::transformString)
					.collect(Collectors.toList());
			final List<String> transformedDataClasses = transformDataClasses(transformedLines, generatedAttributes);
			final File newFile = new File(transformString(path.toString()));
			newFile.getParentFile().mkdirs();
			Files.write(newFile.toPath(), transformedDataClasses);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private static String transformString(String line) {
		return line
				.replace(templateLocation, generatedLocation)
				.replace(templatePackageRoot, generatedPackageRoot)
				.replaceAll(templateName, generatedName)
				.replaceAll(TemplateName, GeneratedName)
				.replaceAll(TEMPLATE_NAME, GENERATED_NAME);
	}

	private static List<String> transformDataClasses(List<String> lines, List<String> newAttributes) {
		final Optional<String> private_string_name = lines.stream()
				.filter(line -> line.contains("private String name"))
				.findAny();
		if (private_string_name.isPresent()) {
			final int indexOf = lines.indexOf(private_string_name.get());
			newAttributes.forEach(newAttribute -> lines.add(indexOf + 1, private_string_name.get().replace("name", newAttribute)));
			lines.remove(indexOf);

		}
		return lines;
	}

	private static List<String> transformScriptClasses(List<String> lines, List<String> newAttributes) {
		final List<String> transformedLines = lines.stream()
				.map(GenerateCRUD::transformString)
				.collect(Collectors.toList());
		final Optional<String> private_string_name = transformedLines.stream()
				.filter(line -> line.contains("name"))
				.findAny();
		if (private_string_name.isPresent()) {
			final int indexOf = transformedLines.indexOf(private_string_name.get());
			newAttributes.forEach(newAttribute -> transformedLines.add(indexOf + 1, private_string_name.get().replace("name", newAttribute)));
			transformedLines.remove(indexOf);
		}
		return transformedLines;
	}

	private static String capitalizeFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
