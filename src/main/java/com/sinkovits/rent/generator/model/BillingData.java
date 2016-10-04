package com.sinkovits.rent.generator.model;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement(name = "data")
public class BillingData {

	private String header;
	private String date;
	private String landLord;
	private String tenant;
	private String rentValue;
	private String rentValueText;
	private List<BillingItem> items = Lists.newArrayList();
	private BillingFiles files = new BillingFiles();

	@XmlElement
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@XmlElement
	public Integer getSum() {
		Integer sum = 0;
		for (BillingItem item : items) {
			if (item.getPrice() != null)
				sum += item.getPrice();
		}
		return sum;
	}

	@XmlElement(name = "bill")
	public List<BillingItem> getItems() {
		return items;
	}

	@XmlElement
	public List<String> getFiles() {
		return files.getFiles();
	}

	public void setFiles(BillingFiles files) {
		this.files = files;
	}

	@XmlElement
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlElement
	public String getLandLord() {
		return landLord;
	}

	public void setLandLord(String landLord) {
		this.landLord = landLord;
	}

	@XmlElement
	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	@XmlElement
	public String getRentValue() {
		return rentValue;
	}

	public void setRentValue(String rentValue) {
		this.rentValue = rentValue;
	}

	@XmlElement
	public String getRentValueText() {
		return rentValueText;
	}

	public void setRentValueText(String rentValueText) {
		this.rentValueText = rentValueText;
	}

	public static class Builder {
		private String header;
		private String date;
		private String landLord;
		private String tenant;
		private String rentValue;
		private String rentValueText;
		private List<BillingItem> items = Lists.newArrayList();
		private BillingFiles files = new BillingFiles();

		public Builder withHeader(String header) {
			this.header = header;
			return this;
		}

		public Builder withDate(String date) {
			this.date = date;
			return this;
		}

		public Builder withLandLord(String landLord) {
			this.landLord = landLord;
			return this;
		}

		public Builder withTenant(String tenant) {
			this.tenant = tenant;
			return this;
		}

		public Builder withRentValue(String rentValue) {
			this.rentValue = rentValue;
			return this;
		}

		public Builder withRentValueText(String rentValueText) {
			this.rentValueText = rentValueText;
			return this;
		}

		public Builder addItems(Collection<BillingItem> items) {
			this.items.addAll(items);
			return this;
		}

		public Builder addItem(BillingItem item) {
			this.items.add(item);
			return this;
		}

		public Builder withBillingFiles(BillingFiles files) {
			this.files = files;
			return this;
		}

		public Builder addBillingFiles(BillingFiles files) {
			this.files.getFiles().addAll(files.getFiles());
			return this;
		}

		public Builder addBillingFile(String file) {
			this.files.getFiles().add(file);
			return this;
		}
		
		public Builder withBillingData(BillingData data) {
			header = data.getHeader();
			date = data.getDate();
			landLord = data.getLandLord();
			tenant = data.getTenant();
			rentValue = data.getRentValue();
			rentValueText = data.getRentValueText();
			items = data.getItems();
			files = data.files;
			return this;

		}

		public BillingData build() {
			BillingData result = new BillingData();
			result.setHeader(header);
			result.setDate(date);
			result.setLandLord(landLord);
			result.setTenant(tenant);
			result.setRentValue(rentValue);
			result.setRentValueText(rentValueText);
			result.setFiles(files);
			result.getItems().addAll(items);
			return result;
		}
	}
	
	public static Builder builder(){
		return new Builder();
	}
}