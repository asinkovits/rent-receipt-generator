package com.sinkovits.rent.generator;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sinkovits.rent.generator.model.BillingData;
import com.sinkovits.rent.generator.model.BillingData.Builder;
import com.sinkovits.rent.generator.util.ArgumentResolver;
import com.sinkovits.rent.generator.util.DataWriter;
import com.sinkovits.rent.generator.xml.BillingDataBuilderCommand;

public class XmlGenerator implements Application {

	@Autowired
	private List<BillingDataBuilderCommand> commands;
	@Autowired
	private DataWriter dataWriter;
	@Autowired
	private ArgumentResolver argsResolver;

	public void setDataWriter(DataWriter dataWriter) {
		this.dataWriter = dataWriter;
	}

	public void setArgsResolver(ArgumentResolver argsResolver) {
		this.argsResolver = argsResolver;
	}

	public void addCommand(BillingDataBuilderCommand command) {
		this.commands.add(command);
	}

	@Override
	public void execute() throws Exception {
		Path workDirPath = argsResolver.getWorkDir();
		String outputFile = argsResolver.getDataFile();
		Builder dataBuilder = BillingData.builder();
		for (BillingDataBuilderCommand command : commands) {
			command.execute(dataBuilder);
		}
		dataWriter.write(dataBuilder.build(), workDirPath.resolve(outputFile).toFile());
	}

}
