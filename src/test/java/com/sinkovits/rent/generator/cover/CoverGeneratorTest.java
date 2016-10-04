package com.sinkovits.rent.generator.cover;

import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinkovits.rent.generator.GeneratorConfig;
import com.sinkovits.rent.generator.cover.CoverGenerator;
import com.sinkovits.rent.generator.exception.GenerationFailedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GeneratorConfig.class })
public class CoverGeneratorTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	@Autowired
	private CoverGenerator generator;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void testOutputFileIsCreated() throws GenerationFailedException, IOException {
		// Given
		File file = temporaryFolder.newFile();
		Path workDir = file.getParentFile().toPath();
		String inputFile = file.getName();
		String outputFile = "test.pdf";

		// When
		generator.generate(workDir.resolve(inputFile), workDir.resolve(outputFile));

		// Then
		assertTrue(workDir.resolve(outputFile).toFile().exists());
	}
}
