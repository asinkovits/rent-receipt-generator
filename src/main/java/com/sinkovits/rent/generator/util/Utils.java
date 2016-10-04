package com.sinkovits.rent.generator.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import com.google.common.collect.Lists;

public class Utils {

	private static final String JPG = ".jpg";
	private static final String JPEG = ".jpeg";
	private static final String PNG = ".png";
	private static final String PDF = ".pdf";
	private static final String XML = ".xml";

	public static final String BATCH_PDF_NAME = "batch.pdf";
	public static final String COVER_PDF_NAME = "cover.pdf";
	private static final List<String> IGNORED_NAMES = Lists.newArrayList(COVER_PDF_NAME, BATCH_PDF_NAME);

	private static final FilenameFilter XML_FILE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return isXml(name);
		}
	};
	private static final FilenameFilter PDF_FILE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return isPdf(name) && !IGNORED_NAMES.contains(name);
		}
	};

	private Utils() {
		super();
	}

	public static boolean isImage(String file) {
		String lowerCase = file.toLowerCase();
		return lowerCase.endsWith(JPG) || lowerCase.endsWith(JPEG) || lowerCase.endsWith(PNG);
	}

	public static boolean isPdf(String file) {
		return file.toLowerCase().endsWith(PDF);
	}

	public static boolean isXml(String file) {
		return file.toLowerCase().endsWith(XML);
	}

	public static File[] getPdfFiles(String workDir) {
		File dir = new File(workDir);
		File[] pdfFiles = dir.listFiles(PDF_FILE_FILTER);
		return pdfFiles;
	}

	public static File[] getXmlFiles(String workDir) {
		File dir = new File(workDir);
		File[] xmlFiles = dir.listFiles(XML_FILE_FILTER);
		return xmlFiles;
	}
}
