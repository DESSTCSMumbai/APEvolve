package com.dessapi.util;

import java.io.IOException;
import java.util.Properties;

import com.dessapi.bean.ConfigParams;

public class ConfigFileUtil {
	Properties prop = new Properties();
	
	public ConfigFileUtil() {
		try {
			prop.load(ConfigFileUtil.class.getClassLoader().getResourceAsStream("db_config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ConfigParams getDbConfig()
	{		
		ConfigParams configObj = new ConfigParams();
		try {		    
			configObj.setDbUsername(prop.getProperty("DB_USERNAME"));
			configObj.setDbPassword(prop.getProperty("DB_PASSWORD"));
		} 
		catch (Exception ex) {
		    ex.printStackTrace();
		}
		return configObj;
	}	
	
}
