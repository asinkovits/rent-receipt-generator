package com.sinkovits.rent.generator;

import java.nio.file.Path;

public class GeneratorContext {

	private Path workDirPath;
	private String dataFile;
	private String coverFile;
	private String batchFile;

	public GeneratorContext(Path workDirPath, String dataFile, String coverFile, String batchFile) {
		super();
		this.workDirPath = workDirPath;
		this.dataFile = dataFile;
		this.coverFile = coverFile;
		this.batchFile = batchFile;
	}

	public Path getWorkDirPath() {
		return workDirPath;
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

	public static class Builder {

		private Path workDirPath;
		private String dataFile;
		private String coverFile;
		private String batchFile;

		public Builder withWorkDirPath(Path workDirPath) {
			this.workDirPath = workDirPath;
			return this;
		}

		public Builder withDataFile(String dataFile) {
			this.dataFile = dataFile;
			return this;
		}

		public Builder withCover(String coverFile) {
			this.coverFile = coverFile;
			return this;
		}

		public Builder withBatch(String batchFile) {
			this.batchFile = batchFile;
			return this;
		}

		public GeneratorContext build() {
			return new GeneratorContext(workDirPath, dataFile, coverFile, batchFile);
		}

	}

	public static Builder builder() {
		return new Builder();
	}

}
