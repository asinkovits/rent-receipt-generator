package com.sinkovits.rent.generator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.transform.TransformerFactory;

import org.apache.fop.apps.FopFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

@Configuration
// @ComponentScan({ "com.sinkovits.rent.generator.cover",
// "com.sinkovits.rent.generator.batch",
// "com.sinkovits.rent.generator.parser" })
@ComponentScan({ "com.sinkovits.rent.generator.parser",
	 			 "com.sinkovits.rent.generator.xml",
				 "com.sinkovits.rent.generator.util" })

@PropertySource({ "classpath:application.properties" })
public class GeneratorConfig {

	@Autowired
	private ResourceLoader resourceLoader;

	@Bean(name = "cover")
	public Application coverPdfGeneratorApp() {
		return new CoverPdfGenerator();
	}

	@Bean(name = "xml")
	public Application xmlGeneratorApp() {
		return new XmlGenerator();
	}

	@Bean(name = "batch")
	public Application batchGeneratorApp() {
		return new BatchPdfGenerator();
	}

	@Bean
	public TransformerFactory transformerFactory() {
		return TransformerFactory.newInstance();
	}

	@Bean
	public FopFactory fopFactory() throws SAXException, IOException, URISyntaxException {
		Resource fopConfigResource = resourceLoader.getResource(CoverPdfGenerator.FOP_XCONF);
		return FopFactory.newInstance(new URI("."), fopConfigResource.getInputStream());
	}
	//
	// @Bean
	// public BillingFileHandler xmlFileHandler() throws JAXBException {
	// return new BillingFileHandler();
	// }
	//
	// @Bean
	// public PrefixDisplayNameResolver
	// nameResolver(@Value("${display.name.mapping}") String mapping) {
	// return new PrefixDisplayNameResolver(mapping);
	// }
	//

}
