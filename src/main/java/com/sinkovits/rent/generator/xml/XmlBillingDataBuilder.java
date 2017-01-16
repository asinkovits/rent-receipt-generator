package com.sinkovits.rent.generator.xml;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.core.DataReader;
import com.sinkovits.rent.generator.model.BillingData;
import com.sinkovits.rent.generator.model.BillingData.Builder;
import com.sinkovits.rent.generator.model.BillingFiles;
import com.sinkovits.rent.generator.model.BillingItem;
import com.sinkovits.rent.generator.util.ParamResolver;
import com.sinkovits.rent.generator.util.Utils;

@Component
@Order(value=1)
public class XmlBillingDataBuilder implements BillingDataBuilderCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlBillingDataBuilder.class);

	@Autowired
	public DataReader dataReader;
	@Autowired
	private ParamResolver argumentResolver;
	private Builder builder;

	public void setDataReader(DataReader dataReader) {
		this.dataReader = dataReader;
	}

	@Override
	public void execute(Builder builder) {
		this.builder = builder;
		File workDir = argumentResolver.getWorkDir().toFile();
		System.out.println(workDir);
		String[] xmlFiles = workDir.list((dir, fileName) -> Utils.isXml(fileName));
		Arrays.stream(xmlFiles)
				.forEach(fileName -> processXml(fileName));
	}

	private void processXml(String fileName) {
		if(!argumentResolver.getDataFile().equals(fileName)){
			File file = argumentResolver.getWorkDir().resolve(fileName).toFile();
			Object xml = dataReader.read(file);
			tryPopulate(xml);
		}
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
