package com.dessapi.util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.dessapi.bean.ConfigParams;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
 
public class DBUtil {
	Context ctx = null;
	Connection dbConn = null;

	public Connection getConnection() {
		try {
			if (dbConn == null) {							
				createConnection();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}

	public void createConnection() {
		DataSource ds = null;
		ConfigFileUtil configUtilObj = new ConfigFileUtil();		 
		try {
			ConfigParams paramsObj = configUtilObj.getDbConfig();
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyPortalDB");	
			ds.getConnection();
			//ds.getConnection(paramsObj.getDbUsername(), paramsObj.getDbPassword());			
			dbConn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void closeConnection() {
		try {
			if (dbConn != null) {
				dbConn.close();
				dbConn=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
