package com.sinkovits.rent.generator.cover;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.exception.GenerationFailedException;

@Component
public class CoverGenerator {

	public static final String FOP_XCONF = "classpath:fop.xconf";

	public CoverGenerator() {
		super();
	}

	@Value("${cover.fop.templateFile}")
	private String templateFile;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private TransformerFactory transformerFactory;
	@Autowired
	private FopFactory fopFactory;

	public void generate(Path inputFilePath, Path outputFilePath) throws GenerationFailedException {
		Resource template = resourceLoader.getResource(templateFile);
		try (BufferedReader in = Files.newBufferedReader(inputFilePath);
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(outputFilePath));
				InputStream templateInputStream = template.getInputStream()) {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(templateInputStream));
			Source src = new StreamSource(in);
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);
		} catch (TransformerException | FOPException | IOException ex) {
			throw new GenerationFailedException("PDF generation failed!", ex);
		}
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setTransformerFactory(TransformerFactory transformerFactory) {
		this.transformerFactory = transformerFactory;
	}

	public void setFopFactory(FopFactory fopFactory) {
		this.fopFactory = fopFactory;
	}

}
