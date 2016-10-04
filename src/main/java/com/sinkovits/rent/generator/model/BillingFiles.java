package com.sinkovits.rent.generator.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement(name = "files")
public class BillingFiles {

	private List<String> files = Lists.newArrayList();

	@XmlElement(name = "file")
	public List<String> getFiles() {
		return files;
	}
}
