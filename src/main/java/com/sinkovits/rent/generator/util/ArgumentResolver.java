package com.sinkovits.rent.generator.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ArgumentResolver {

	private String[] args;
	@Value("${applicationName}")
	private String applicationName;
	@Value("${dataFile}")
	private String dataFile;
	@Value("${coverFile}")
	private String coverFile;
	@Value("${batchFile}")
	private String batchFile;
	@Value("${workDir}")
	private String workDir;

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getAppName() {
		return args.length > 0 ? args[0] : applicationName;
	}

	public Path getWorkDir() {
		return Paths.get(workDir);
	}

	public String getDataFile() {
		return dataFile;
	}

	public String getCoverFile() {
		return coverFile;
	}

	public String getBatchFile() {
		return batchFile;
	}

}
