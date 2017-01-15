package com.sinkovits.rent.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.sinkovits.rent.generator.model.BillingData;
import com.sinkovits.rent.generator.util.ArgumentResolver;
import com.sinkovits.rent.generator.util.DataReader;
import com.sinkovits.rent.generator.util.Utils;

public class BatchPdfGenerator implements Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchPdfGenerator.class);

	@Autowired
	private DataReader reader;
	@Autowired
	private ArgumentResolver argResolver;

	@Override
	public void execute() {
		Path workDirPath = argResolver.getWorkDir();
		String inputFile = argResolver.getDataFile();
		String outputFile = argResolver.getBatchFile();

		BillingData billingData = reader.read(workDirPath.resolve(inputFile).toFile(), BillingData.class);
		List<PDDocument> pdfFiles = Lists.newArrayList();
		try {
			PDDocument doc = new PDDocument();
			pdfFiles.add(doc);

			addCoverToBatch(pdfFiles, doc, workDirPath);
			addFilesToBatch(billingData, pdfFiles, doc, workDirPath);

			doc.save(workDirPath.resolve(outputFile).toFile());
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			for (PDDocument pdDocument : pdfFiles) {
				try {
					pdDocument.close();
				} catch (IOException ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
		}
	}

	private void addCoverToBatch(List<PDDocument> pdfFiles, PDDocument doc, Path workDirPath) throws IOException {
		File file = workDirPath.resolve(argResolver.getCoverFile()).toFile();
		if (file.exists()) {
			PDDocument cover = PDDocument.load(file);
			pdfFiles.add(cover);
			merge(doc, cover);
		}
	}

	private void merge(PDDocument doc1, PDDocument doc2) throws IOException {
		for (PDPage page : doc2.getPages()) {
			doc1.addPage(page);
		}
	}

	private void addFilesToBatch(BillingData billingData, List<PDDocument> pdfFiles, PDDocument doc, Path workDirPath)
			throws IOException {
		addFiles(pdfFiles, doc, billingData.getFiles(), workDirPath);
	}

	private void addFiles(List<PDDocument> pdfFiles, PDDocument doc, List<String> files, Path workDirPath)
			throws IOException {
		for (String file : files) {
			File abs = resolve(file, workDirPath);

			if (Utils.isPdf(file)) {
				PDDocument bill = PDDocument.load(abs);
				pdfFiles.add(bill);
				merge(doc, bill);
			} else if (Utils.isImage(file)) {
				addImage(doc, abs);
			}
		}
	}

	private File resolve(String file, Path workDirPath) {
		File filePath = new File(file);
		if (!filePath.exists() || !filePath.isFile()) {
			filePath = workDirPath.resolve(file).toFile();
		}
		return filePath;
	}

	private void addImage(PDDocument doc, File file) throws IOException {
		if (file.exists()) {
			PDPage page = new PDPage();
			PDImageXObject pdImage = PDImageXObject.createFromFile(file.getAbsolutePath(), doc);
			PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);
			float scale = calculateScale(pdImage, page.getBBox());
			contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
			contentStream.close();
			doc.addPage(page);
		} else {
			LOGGER.warn("File  %d not found!, Skipping.", file.getAbsolutePath());
		}
	}

	private float calculateScale(PDImageXObject pdImage, PDRectangle bBox) {
		float xs = bBox.getWidth() / pdImage.getWidth();
		float ys = bBox.getHeight() / pdImage.getHeight();
		return Math.min(xs, ys);
	}

}
