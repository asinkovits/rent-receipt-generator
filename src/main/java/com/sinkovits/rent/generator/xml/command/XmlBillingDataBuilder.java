package com.sinkovits.rent.generator.xml.command;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.GeneratorContext;
import com.sinkovits.rent.generator.model.BillingData;
import com.sinkovits.rent.generator.model.BillingData.Builder;
import com.sinkovits.rent.generator.model.BillingFiles;
import com.sinkovits.rent.generator.model.BillingItem;
import com.sinkovits.rent.generator.util.DataReader;
import com.sinkovits.rent.generator.util.Utils;

@Component
public class XmlBillingDataBuilder implements BillingDataBuilderCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlBillingDataBuilder.class);

	@Autowired
	public DataReader dataReader;
	private Builder builder;

	public void setDataReader(DataReader dataReader) {
		this.dataReader = dataReader;
	}

	@Override
	public void execute(GeneratorContext context, Builder builder) {
		this.builder = builder;
		File workDir = context.getWorkDirPath().toFile();
		String[] xmlFiles = workDir.list((dir, fileName) -> Utils.isXml(fileName));
		Arrays.stream(xmlFiles)
				.forEach(fileName -> tryPopulate(dataReader.read(context.getWorkDirPath().resolve(fileName).toFile())));
	}

	public void populate(BillingFiles files) {
		if (files != null)
			builder.withBillingFiles(files);
	}

	public void populate(BillingData data) {
		builder.withBillingData(data);
	}

	public void populate(Collection<BillingItem> items) {
		builder.addItems(items);
	}

	public void populate(BillingItem item) {
		builder.addItem(item);
	}

	public void tryPopulate(Object parsed) {
		if (parsed instanceof BillingFiles) {
			populate((BillingFiles) parsed);
		} else if (parsed instanceof BillingItem) {
			populate((BillingItem) parsed);
		} else if (parsed instanceof BillingData) {
			populate((BillingData) parsed);
		} else {
			LOGGER.warn("Population failed. Unknown type.");
		}
	}

}
