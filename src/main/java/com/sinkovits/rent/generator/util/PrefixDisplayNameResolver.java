package com.sinkovits.rent.generator.util;

import java.util.Map;

import com.google.common.base.Splitter;

public class PrefixDisplayNameResolver implements DisplayNameResolver {
	
	private static String GROUP_SEPARATOR = ",";
	private static String KEY_VALUE_SEPARATOR = ":";
	
	public PrefixDisplayNameResolver(String mapping) {
		super();
		this.mapping = Splitter.on(GROUP_SEPARATOR).withKeyValueSeparator(KEY_VALUE_SEPARATOR).split(mapping);
		System.out.println("");
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
