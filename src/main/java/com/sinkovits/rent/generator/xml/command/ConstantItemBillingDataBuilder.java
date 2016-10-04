package com.sinkovits.rent.generator.xml.command;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.GeneratorContext;
import com.sinkovits.rent.generator.model.BillingData.Builder;
import com.sinkovits.rent.generator.model.BillingItem;
import com.sinkovits.rent.generator.util.DataReader;

@Component
public class ConstantItemBillingDataBuilder implements BillingDataBuilderCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConstantItemBillingDataBuilder.class);
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private DataReader streamReader;
	@Value("${files.persistentBills}")
	private String persistentBills;

	@Override
	public void execute(GeneratorContext context, Builder builder) {
		Resource resource = resourceLoader.getResource(persistentBills);
		try (InputStream stream = resource.getInputStream()) {
			BillingItem item = streamReader.read(stream, BillingItem.class);
			builder.addItem(item);
		} catch (Exception ex) {
			LOGGER.error("Could not populte, fixed billing items.", ex);
		}
	}
}
