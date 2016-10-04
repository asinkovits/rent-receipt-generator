package com.sinkovits.rent.generator.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.sinkovits.rent.generator.model.BillingData;
import com.sinkovits.rent.generator.model.BillingFiles;
import com.sinkovits.rent.generator.model.BillingItem;

@Component
public class BillingFileHandler implements DataReader, DataWriter {

	private JAXBContext jaxbContext;
	private Unmarshaller jaxbUnmarshaller;
	private Marshaller jaxbMarshaller;

	public BillingFileHandler() throws JAXBException {
		super();
		jaxbContext = JAXBContext.newInstance(BillingItem.class, BillingData.class, BillingFiles.class);
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	}

	public Object read(File file) {
		try {
			return jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public <T> T read(File file, Class<T> clazz) {
		return (T) read(file);
	}

	public <T> T read(InputStream is, Class<T> clazz) {
		try {
			return (T) jaxbUnmarshaller.unmarshal(is);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void write(Object object, File file) {
		try {
			jaxbMarshaller.marshal(object, file);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void write(Object object, OutputStream os) {
		try {
			jaxbMarshaller.marshal(object, os);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}
}
