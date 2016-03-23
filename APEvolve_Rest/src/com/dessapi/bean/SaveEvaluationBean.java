package com.dessapi.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class SaveEvaluationBean {
	
	private String compName;
	private ArrayList<FeatureRating>  feature_list;
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public ArrayList<FeatureRating> getFeature_list() {
		return feature_list;
	}
	public void setFeature_list(ArrayList<FeatureRating> feature_list) {
		this.feature_list = feature_list;
	}
	
	@Override
	public String toString(){
		
		
		return this.compName;
		
		
		
	}

public HashMap<String, Integer> toHashMap(ArrayList<FeatureRating> frt){
	HashMap<String, Integer> productMap = new HashMap<String, Integer>();
	int i=0;
	for (FeatureRating fr : frt) {
	   productMap.put(frt.get(i).getFeature_id(), frt.get(i).getRating());
	   i++;
	}
	return productMap;
	
}

}
