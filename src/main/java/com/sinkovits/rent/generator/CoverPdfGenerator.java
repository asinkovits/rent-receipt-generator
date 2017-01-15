package com.sinkovits.rent.generator;

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

import com.sinkovits.rent.generator.exception.GenerationFailedException;
import com.sinkovits.rent.generator.util.ArgumentResolver;

public class CoverPdfGenerator implements Application {
	
	public static final String FOP_XCONF = "classpath:fop.xconf";

	@Value("${cover.fop.templateFile}")
	private String templateFile;

	@Autowired
	private ArgumentResolver argsResolver;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private TransformerFactory transformerFactory;
	@Autowired
	private FopFactory fopFactory;

	public void execute() throws GenerationFailedException {
		Path input = argsResolver.getWorkDir().resolve(argsResolver.getDataFile());
		Path output = argsResolver.getWorkDir().resolve(argsResolver.getCoverFile());
		Resource template = resourceLoader.getResource(templateFile);
		try (BufferedReader in = Files.newBufferedReader(input);
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(output));
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

}
