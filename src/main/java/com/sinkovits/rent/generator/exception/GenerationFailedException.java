package com.sinkovits.rent.generator.exception;

public class GenerationFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public GenerationFailedException(String string, Exception ex) {
		super(string, ex);
	}

}
