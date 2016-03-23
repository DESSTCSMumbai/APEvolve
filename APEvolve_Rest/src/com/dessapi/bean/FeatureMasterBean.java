package com.dessapi.bean;

import java.util.ArrayList;

public class FeatureMasterBean {
	private String mstCategoryId;	 
	private String feature;	
	private String mstProductId;
	private Integer rating;
	private String comment;
	private String reference;
	private String relevance;
	
	public String getMstCategoryId() {
		return mstCategoryId;
	}
	public void setMstCategoryId(String mstCategoryId) {
		this.mstCategoryId = mstCategoryId;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getMstProductId() {
		return mstProductId;
	}
	public void setMstProductId(String mstProductId) {
		this.mstProductId = mstProductId;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getRelevance() {
		return relevance;
	}
	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}		
}
