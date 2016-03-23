package com.dessapi.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class FeatureRating {
private String feature_id;
private Integer rating;

public String getFeature_id() {
	return feature_id;
}

public void setFeature_id(String feature_id) {
	this.feature_id = feature_id;
}

public Integer getRating() {
	return rating;
}

public void setRating(Integer rating) {
	this.rating = rating;
}

@Override
public String toString(){
	
	
	return this.feature_id +":" +this.rating;
	
	
	
}



}
