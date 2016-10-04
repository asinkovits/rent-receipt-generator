package com.sinkovits.rent.generator.parser;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.GeneratorContext;
import com.sinkovits.rent.generator.model.BillingData.Builder;
import com.sinkovits.rent.generator.model.BillingItem;
import com.sinkovits.rent.generator.util.DisplayNameResolver;
import com.sinkovits.rent.generator.util.Utils;
import com.sinkovits.rent.generator.xml.command.BillingDataBuilderCommand;

@Component
public class PdfParser implements BillingDataBuilderCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdfParser.class);
	private static final String SEPARATOR = ",";

	private DisplayNameResolver nameResolver;
	private TextParser companyParser;
	private TextParser amountParser;

	@Autowired
	public void setCompanyParser(@Value("${parseText.company}") String company) {
		String[] split = company.split(SEPARATOR);
		this.companyParser = new TextParser(split[0], split[1]);
	}

	@Autowired
	public void setAmountParser(@Value("${parseText.amount}") String amount) {
		String[] split = amount.split(SEPARATOR);
		this.amountParser = new TextParser(split[0], split[1]);
	}

	@Autowired
	public void setNameResolver(DisplayNameResolver nameResolver) {
		this.nameResolver = nameResolver;
	}

	public void execute(GeneratorContext context, Builder builder) {
		String[] files = context.getWorkDirPath().toFile()
				.list((dir, fileName) -> (Utils.isPdf(fileName) && !context.getCoverFile().equals(fileName)));
		for (String file : files) {
			File pdfFile = context.getWorkDirPath().resolve(file).toFile();
			Optional<BillingItem> parsedItem = parse(pdfFile);
			if (parsedItem.isPresent()) {
				builder.addItem(parsedItem.get());
				builder.addBillingFile(pdfFile.getAbsolutePath());
			} else {
				LOGGER.warn("Couldn't parse [%s] file. Skipping.", pdfFile.getAbsolutePath());
			}
		}
	}

	private Optional<BillingItem> parse(File pdf) {
		String parsedText;
		try {
			parsedText = parsePdf(pdf);
			Optional<String> parseAmountToPay = parseAmountToPay(parsedText);
			Optional<String> parseProviderName = parseProviderName(parsedText);
			if (parseAmountToPay.isPresent() && parseProviderName.isPresent()) {
				String providerName = parseProviderName.get();
				int amount = parseInt(parseAmountToPay.get());
				String resolvedName = nameResolver.resolve(providerName);
				BillingItem item = new BillingItem();
				item.setName(resolvedName);
				item.setPrice(amount);
				return Optional.of(item);
			}
		} catch (IOException e) {
			LOGGER.warn("Parse failed for %s file", pdf.getAbsolutePath());
		}
		return Optional.empty();
	}

	private String parsePdf(File file) throws IOException {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		try {
			PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(1);
			String parsedText = pdfStripper.getText(pdDoc);
			return parsedText;
		} finally {
			close(cosDoc);
		}

	}

	private void close(COSDocument cosDoc) {
		if (cosDoc != null)
			try {
				cosDoc.close();
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
	}

	private Optional<String> parseProviderName(String parsedText) {
		Optional<String> parseText = companyParser.parseText(parsedText);
		if (parseText.isPresent()) {
			return Optional.of(parseText.get().trim());
		} else {
			return parseText;
		}
	}

	private Optional<String> parseAmountToPay(String parsedText) {
		Optional<String> parseText = amountParser.parseText(parsedText);
		if (parseText.isPresent()) {
			return Optional.of(parseText.get().replaceAll("\\s+", ""));
		} else {
			return parseText;
		}
	}
}
