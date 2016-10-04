package com.sinkovits.rent.generator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerFactory;

import org.apache.fop.apps.FopFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import com.sinkovits.rent.generator.cover.CoverGenerator;
import com.sinkovits.rent.generator.parser.PdfParser;
import com.sinkovits.rent.generator.util.BillingFileHandler;
import com.sinkovits.rent.generator.util.DataWriter;
import com.sinkovits.rent.generator.util.PrefixDisplayNameResolver;
import com.sinkovits.rent.generator.xml.XmlGenerator;
import com.sinkovits.rent.generator.xml.command.ConstantItemBillingDataBuilder;
import com.sinkovits.rent.generator.xml.command.MetadataConfigBillingDataBuilder;
import com.sinkovits.rent.generator.xml.command.XmlBillingDataBuilder;

@Configuration
@ComponentScan({ "com.sinkovits.rent.generator.cover", "com.sinkovits.rent.generator.batch",
		"com.sinkovits.rent.generator.xml.command", "com.sinkovits.rent.generator.parser" })
@PropertySource({ "classpath:application.properties" })
public class GeneratorConfig {

	@Autowired
	private ResourceLoader resourceLoader;

	@Bean
	public TransformerFactory transformerFactory() {
		return TransformerFactory.newInstance();
	}

	@Bean
	public FopFactory fopFactory() throws SAXException, IOException, URISyntaxException {
		Resource fopConfigResource = resourceLoader.getResource(CoverGenerator.FOP_XCONF);
		return FopFactory.newInstance(new URI("."), fopConfigResource.getInputStream());
	}

	@Bean
	public BillingFileHandler xmlFileHandler() throws JAXBException {
		return new BillingFileHandler();
	}

	@Bean
	public PrefixDisplayNameResolver nameResolver(@Value("${display.name.mapping}") String mapping) {
		return new PrefixDisplayNameResolver(mapping);
	}

	@Bean
	public XmlGenerator xmlGenerator(MetadataConfigBillingDataBuilder metadataCmd,
			ConstantItemBillingDataBuilder constantCmd, XmlBillingDataBuilder xmlCmd, PdfParser pdfCmd,
			DataWriter dataWriter) {
		XmlGenerator xmlGenerator = new XmlGenerator();
		xmlGenerator.addCommand(metadataCmd);
		xmlGenerator.addCommand(constantCmd);
		xmlGenerator.addCommand(xmlCmd);
		xmlGenerator.addCommand(pdfCmd);
		xmlGenerator.setDataWriter(dataWriter);
		return xmlGenerator;
	}
}
