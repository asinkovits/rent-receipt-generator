package com.sinkovits.rent.generator.xml;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.sinkovits.rent.generator.GeneratorContext;
import com.sinkovits.rent.generator.model.BillingData;
import com.sinkovits.rent.generator.model.BillingData.Builder;
import com.sinkovits.rent.generator.util.DataWriter;
import com.sinkovits.rent.generator.xml.command.BillingDataBuilderCommand;

public class XmlGenerator {

	private List<BillingDataBuilderCommand> commands = new ArrayList<>();
	private DataWriter dataWriter;

	public void addCommand(BillingDataBuilderCommand command) {
		this.commands.add(command);
	}

	public void setDataWriter(DataWriter dataWriter) {
		this.dataWriter = dataWriter;
	}

	public void generate(GeneratorContext context) {
		Path workDirPath = context.getWorkDirPath();
		String outputFile = context.getDataFile();
		Builder dataBuilder = BillingData.builder();
		for (BillingDataBuilderCommand command : commands) {
			command.execute(context, dataBuilder);
		}
		dataWriter.write(dataBuilder.build(), workDirPath.resolve(outputFile).toFile());
	}

}
