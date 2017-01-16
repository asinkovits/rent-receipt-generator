package com.sinkovits.rent.generator;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sinkovits.rent.generator.core.Application;
import com.sinkovits.rent.generator.util.ParamResolver;

public class ApplicationStarter {

	public static void main(String[] args) throws Exception {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(GeneratorConfig.class)) {
			AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
			
			ParamResolver argsResolver = beanFactory.getBean(ParamResolver.class);
			argsResolver.setArgs(args);
			
			String applicationName = argsResolver.getAppName();

			Application app = beanFactory.getBean(applicationName, Application.class);
			app.execute();

		}
	}

}
