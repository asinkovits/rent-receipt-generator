package com.sinkovits.rent.generator.util;

public class Utils {

	private static final String JPG = ".jpg";
	private static final String JPEG = ".jpeg";
	private static final String PNG = ".png";
	private static final String PDF = ".pdf";
	private static final String XML = ".xml";

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

}
