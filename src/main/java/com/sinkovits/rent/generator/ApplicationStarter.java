package com.sinkovits.rent.generator;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sinkovits.rent.generator.batch.BatchGenerator;
import com.sinkovits.rent.generator.cover.CoverGenerator;
import com.sinkovits.rent.generator.exception.GenerationFailedException;
import com.sinkovits.rent.generator.xml.XmlGenerator;

public class ApplicationStarter {

	public static void main(String[] args) throws GenerationFailedException {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GeneratorConfig.class)) {
			Path workDir = Paths.get("d:\\workspace\\test");
			String dataFile = "data.xml";
			String coverFile = "cover.pdf";
			String batchFile = "batch.pdf";

			GeneratorContext context = GeneratorContext.builder().withWorkDirPath(workDir).withDataFile(dataFile)
					.withCover(coverFile).withBatch(batchFile).build();

			AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
			XmlGenerator xml = beanFactory.getBean(XmlGenerator.class);
			CoverGenerator cover = beanFactory.getBean(CoverGenerator.class);
			BatchGenerator batch = beanFactory.getBean(BatchGenerator.class);

			xml.generate(context);
			cover.generate(workDir.resolve(dataFile), workDir.resolve(coverFile));
			batch.generate(context);

		}
	}

}
