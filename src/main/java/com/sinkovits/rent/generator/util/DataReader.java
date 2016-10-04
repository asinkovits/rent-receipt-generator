package com.sinkovits.rent.generator.util;

import java.io.File;
import java.io.InputStream;

public interface DataReader {

	<T> T read(InputStream stream, Class<T> clazz);
	
	<T> T read(File file, Class<T> clazz);

	Object read(File file);
}
