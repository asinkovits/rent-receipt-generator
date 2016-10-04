package com.sinkovits.rent.generator.util;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.sinkovits.rent.generator.model.BillingData;


public class BillingDataMergerTest {

	private BillingDataMerger merger;

	@Before
	public void setUp() {
		merger = new BillingDataMerger();
	}

	@Test
	public void testEmptyMetadataInSecondItem() {
		// Given
		String header = "testHeader";
		String date = "2016-10-03";
		String landLord = "Landlord";
		String tenant = "Tenant";
		String rentValue = "100";
		String rentValueText = "One hundred";

		BillingData first = BillingData.builder()
				.withHeader(header)
				.withDate(date)
				.withLandLord(landLord)
				.withTenant(tenant)
				.withRentValue(rentValue)
				.withRentValueText(rentValueText)
				.build();
		
		BillingData second = BillingData.builder()
				.build();

		// When
		BillingData merged = merger.merge(first, second);
		
		// Then
		assertEquals(header, merged.getHeader());
		assertEquals(date, merged.getDate());
		assertEquals(landLord, merged.getLandLord());
		assertEquals(tenant, merged.getTenant());
		assertEquals(rentValue, merged.getRentValue());
		assertEquals(rentValueText, merged.getRentValueText());
	}

	@Test
	public void testEmptyMetadataInFirstItem() {
		// Given
		String header = "testHeader";
		String date = "2016-10-03";
		String landLord = "Landlord";
		String tenant = "Tenant";
		String rentValue = "100";
		String rentValueText = "One hundred";
		
		BillingData first = BillingData.builder()
				.build();

		BillingData second = BillingData.builder()
				.withHeader(header)
				.withDate(date)
				.withLandLord(landLord)
				.withTenant(tenant)
				.withRentValue(rentValue)
				.withRentValueText(rentValueText)
				.build();

		// When
		BillingData merged = merger.merge(first, second);
		
		// Then
		assertEquals(header, merged.getHeader());
		assertEquals(date, merged.getDate());
		assertEquals(landLord, merged.getLandLord());
		assertEquals(tenant, merged.getTenant());
		assertEquals(rentValue, merged.getRentValue());
		assertEquals(rentValueText, merged.getRentValueText());
	}
}
