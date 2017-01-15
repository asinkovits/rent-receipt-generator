package com.sinkovits.rent.generator.xml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.model.BillingData.Builder;

@Component
@Order(value=2)
public class MetadataConfigBillingDataBuilder implements BillingDataBuilderCommand {

	@Value("${data.landLord}")
	private String landLord;
	@Value("${data.tenantName}")
	private String tenant;
	@Value("${data.rentValue}")
	private String rentValue;
	@Value("${data.rentValueText}")
	private String rentValueText;

	@Override
	public void execute(Builder builder) {
		builder
		.withLandLord(landLord)
		.withTenant(tenant)
		.withRentValue(rentValue)
		.withRentValueText(rentValueText);
	}
	
	
}
