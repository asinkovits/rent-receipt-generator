package com.sinkovits.rent.generator.core;

import java.io.File;
import java.io.OutputStream;

public interface DataWriter {

	void write(Object object, File file);

	void write(Object object, OutputStream os);
}
