package com.sinkovits.rent.generator.xml.command;

import com.sinkovits.rent.generator.GeneratorContext;
import com.sinkovits.rent.generator.model.BillingData.Builder;

public interface BillingDataBuilderCommand {

	void execute(GeneratorContext context, Builder builder);

}
