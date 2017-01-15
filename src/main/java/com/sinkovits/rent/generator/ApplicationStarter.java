package com.sinkovits.rent.generator;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sinkovits.rent.generator.util.ArgumentResolver;

public class ApplicationStarter {

	public static void main(String[] args) throws Exception {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GeneratorConfig.class)) {
			AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
			
			ArgumentResolver argsResolver = beanFactory.getBean(ArgumentResolver.class);
			argsResolver.setArgs(args);
			
			String applicationName = argsResolver.getAppName();

			Application app = beanFactory.getBean(applicationName, Application.class);
			app.execute();

//			GeneratorContext context = GeneratorContext.builder().withWorkDirPath(workDir).withDataFile(dataFile)
//					.withCover(coverFile).withBatch(batchFile).build();
//
//			XmlGenerator xml = beanFactory.getBean(XmlGenerator.class);
//			CoverGenerator cover = beanFactory.getBean(CoverGenerator.class);
//			BatchGenerator batch = beanFactory.getBean(BatchGenerator.class);
//
//			xml.generate(context);
//			cover.generate(workDir.resolve(dataFile), workDir.resolve(coverFile));
//			batch.generate(context);

		}
	}

}
