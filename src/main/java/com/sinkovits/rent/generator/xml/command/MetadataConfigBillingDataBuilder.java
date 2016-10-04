package com.sinkovits.rent.generator.xml.command;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.GeneratorContext;
import com.sinkovits.rent.generator.model.BillingData.Builder;

@Component
public class MetadataConfigBillingDataBuilder implements BillingDataBuilderCommand {

	@Value("${data.landLord}")
	private String landLord;
	@Value("${data.tenantName}")
	private String tenant;
	@Value("${data.rentValue}")
	private String rentValue;
	@Value("${data.rentValueText}")
	private String rentValueText;
	@Value("${data.month}")
	private String month;
	@Value("${data.date}")
	private String date;

	@Override
	public void execute(GeneratorContext context, Builder builder) {
		builder
		.withHeader(month)
		.withDate(date)
		.withLandLord(landLord)
		.withTenant(tenant)
		.withRentValue(rentValue)
		.withRentValueText(rentValueText);
	}
	
	
}
