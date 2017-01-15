package com.sinkovits.rent.generator.util;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

@Component
public class PrefixDisplayNameResolver implements DisplayNameResolver, InitializingBean {

	private static String GROUP_SEPARATOR = ",";
	private static String KEY_VALUE_SEPARATOR = ":";
	
	@Value("${display.name.mapping}")
	private String mappingString;
	
	public PrefixDisplayNameResolver() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.mapping = Splitter.on(GROUP_SEPARATOR).withKeyValueSeparator(KEY_VALUE_SEPARATOR).split(mappingString);
	}
	
	public PrefixDisplayNameResolver(Map<String, String> mapping) {
		super();
		this.mapping = mapping;
	}

	private Map<String, String> mapping;

	public String resolve(String key) {
		for (String item : mapping.keySet()) {
			if (key.startsWith(item)) {
				return mapping.get(item);
			}
		}
		return key;
	}
}
