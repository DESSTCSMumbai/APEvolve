package com.dessapi.bean;

import javax.xml.bind.annotation.XmlRootElement;

public class EvalSessionRegDetails {
	private String companyName;
	private String selectedProducts[];
	private int highVal;
	private int mediumVal;
	private int lowVal;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String[] getSelectedProducts() {
		return selectedProducts;
	}
	public void setSelectedProducts(String[] selectedProducts) {
		this.selectedProducts = selectedProducts;
	}
	public int getHighVal() {
		return highVal;
	}
	public void setHighVal(int highVal) {
		this.highVal = highVal;
	}
	public int getMediumVal() {
		return mediumVal;
	}
	public void setMediumVal(int mediumVal) {
		this.mediumVal = mediumVal;
	}
	public int getLowVal() {
		return lowVal;
	}
	public void setLowVal(int lowVal) {
		this.lowVal = lowVal;
	}	
}
