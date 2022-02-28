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
	private final static String templateName = "version";
	private final static String templatePackageName = "versioning";
	private final static String templateLocation = "src/main/java/genis/learning/docker/" + templatePackageName;
	private final static String templatePackageRoot = "genis.learning.docker." + templatePackageName;
	private final static String TemplateName = capitalizeFirstLetter(templateName);
	private final static String TEMPLATE_NAME = templateName.toUpperCase();

	private final String generatedName;
	private final String generatedLocation;
	private final String generatedPackageRoot;
	private final String GeneratedName;
	private final String GENERATED_NAME;

	private final List<String> generatedAttributes; //Reversed order.

	private static final String script = "\n" +
			"-- changeset genis.guillem.mimo:create-table-versions\n" +
			"CREATE TABLE `versions`\n" +
			"(\n" +
			"    `id`   INT          NOT NULL AUTO_INCREMENT,\n" +
			"    `name` VARCHAR(255) NULL,\n" +
			"    PRIMARY KEY (`id`)\n" +
			");\n";


	public GenerateCRUD(String generatedName, String generatedPackageName, List<String> generatedAttributes) {
		this.generatedName = generatedName;
		this.generatedAttributes = generatedAttributes;
		this.generatedLocation = "src/main/java/genis/learning/docker/" + generatedPackageName.replaceAll("\\.","/");
		this.generatedPackageRoot = "genis.learning.docker." + generatedPackageName;
		this.GeneratedName = capitalizeFirstLetter(generatedName);
		this.GENERATED_NAME = generatedName.toUpperCase();
	}

	public GenerateCRUD(String generatedName, String generatedPackageName, String generatedPackageLocation, List<String> generatedAttributes) {
		this.generatedName = generatedName;
		this.generatedAttributes = generatedAttributes;
		this.generatedLocation = "src/main/java/genis/learning/docker/" + generatedPackageName;
		this.generatedPackageRoot = "genis.learning.docker." + generatedPackageName;
		this.GeneratedName = capitalizeFirstLetter(generatedName);
		this.GENERATED_NAME = generatedName.toUpperCase();
	}

	public void generate() throws IOException {

		final Path templatePath = Path.of(templateLocation);
		Files.walk(templatePath)
				.filter(Files::isRegularFile)
				.forEach(this::copyPath);

		final List<String> splittedScript = Arrays.stream(script.split("\n")).collect(Collectors.toList());
		final List<String> newSplittedScript = transformScriptClasses(splittedScript, this.generatedAttributes);
		Files.write(
				Paths.get("src/main/resources/db/changelog/base_changelog.sql"),
				join(newSplittedScript, '\n').getBytes(),
				StandardOpenOption.APPEND);
	}

	private void copyPath(Path path) {
		try {
			final List<String> lines = Files.readAllLines(path);
			final List<String> transformedLines = lines.stream().map(this::transformString)
					.collect(Collectors.toList());
			final List<String> transformedDataClasses = transformDataClasses(transformedLines, generatedAttributes);
			final File newFile = new File(transformString(path.toString()));
			newFile.getParentFile().mkdirs();
			Files.write(newFile.toPath(), transformedDataClasses);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private String transformString(String line) {
		return line
				.replace(templateLocation, this.generatedLocation)
				.replace(templatePackageRoot, this.generatedPackageRoot)
				.replaceAll(templateName, this.generatedName)
				.replaceAll(TemplateName, this.GeneratedName)
				.replaceAll(TEMPLATE_NAME, this.GENERATED_NAME);
	}

	private static List<String> transformDataClasses(List<String> lines, List<String> newAttributes) {
		//Vo and Entity
		final Optional<String> private_string_name = lines.stream()
				.filter(line -> line.contains("private String name"))
				.findAny();
		if (private_string_name.isPresent()) {
			final int indexOf = lines.indexOf(private_string_name.get());
			newAttributes.forEach(newAttribute -> lines.add(indexOf + 1, private_string_name.get().replace("name", newAttribute)));
			lines.remove(indexOf);
		}

		//service
		final Optional<String> updaterLine = lines.stream()
				.filter(line -> line.contains("entity.setName"))
				.findAny();
		if (updaterLine.isPresent()) {
			final int indexOf = lines.indexOf(updaterLine.get());
			newAttributes.forEach(newAttribute -> lines.add(indexOf + 1, updaterLine.get().replaceAll(capitalizeFirstLetter("name"), capitalizeFirstLetter(newAttribute))));
			lines.remove(indexOf);
		}
		return lines;
	}

	private List<String> transformScriptClasses(List<String> lines, List<String> newAttributes) {
		final List<String> transformedLines = lines.stream()
				.map(this::transformString)
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
