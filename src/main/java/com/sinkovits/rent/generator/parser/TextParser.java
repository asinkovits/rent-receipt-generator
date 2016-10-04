package com.sinkovits.rent.generator.parser;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(TextParser.class);
	private String pre;
	private String post;

	public TextParser(String pre, String post) {
		super();
		this.pre = pre;
		this.post = post;
	}

	public Optional<String> parseText(String text) {
		return parseText(text, pre, post);
	}

	private Optional<String> parseText(String parsedText, String before, String after) {
		int start = parsedText.indexOf(before);
		if (start == -1) {
			LOGGER.warn("Search string [%s] not found in [%s].", before, parsedText);
			return Optional.empty();
		}
		String text = parsedText.substring(start + before.length());
		int end = text.indexOf(after);
		if (end == -1){
			LOGGER.warn("Search string [%s] not found in [%s].", after, parsedText);
			return Optional.empty();

		}
		return Optional.of(text.substring(0, end));
	}
}
