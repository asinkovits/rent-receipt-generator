package com.sinkovits.rent.generator.util;

import com.sinkovits.rent.generator.model.BillingData;

public class BillingDataMerger {

	public BillingData merge(BillingData first, BillingData second) {
		BillingData result = new BillingData();
		result.setDate(second.getDate() != null ? second.getDate() : first.getDate());
		result.setHeader(second.getHeader() != null ? second.getHeader() : first.getHeader());
		result.setLandLord(second.getLandLord() != null ? second.getLandLord() : first.getLandLord());
		result.setTenant(second.getTenant() != null ? second.getTenant() : first.getTenant());
		result.setRentValue(second.getRentValue() != null ? second.getRentValue() : first.getRentValue());
		result.setRentValueText(
				second.getRentValueText() != null ? second.getRentValueText() : first.getRentValueText());
		result.getItems().addAll(first.getItems());
		result.getItems().addAll(second.getItems());
		result.getFiles().addAll(first.getFiles());
		result.getFiles().addAll(second.getFiles());
		return result;
	}
}
