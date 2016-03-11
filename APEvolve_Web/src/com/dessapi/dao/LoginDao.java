package com.dessapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dessapi.bean.UserLogin;
import com.dessapi.util.DBUtil;
import com.dessapi.util.SQLConstantUtil;


public class LoginDao {
	Connection dbConn = null;
	public boolean authenticateUser(String userId, String password) {
		boolean isValidUser = false;
        UserLogin user = getUserByUserId(userId);         
        if( user!=null && (userId.equals(user.getUserName()) && password.equals(user.getPassword()))) {
       	 	isValidUser = true;
        }
        return isValidUser;
    }
 
    public UserLogin getUserByUserId(String userId) {    	 
    	PreparedStatement pstmObj = null;
    	ResultSet loginResultObj = null;
    	UserLogin userLoginObj = new UserLogin();
        try {
        	dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.LOGIN_CHECK_QUERY);
        	pstmObj.setString(1, userId);
        	loginResultObj = pstmObj.executeQuery();
        	if(loginResultObj.next()) {
        		userLoginObj.setUserName(loginResultObj.getString("username"));
        		userLoginObj.setPassword(loginResultObj.getString("password"));
        	}             
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			try {
				if (dbConn != null) {
					dbConn.close();
					dbConn = null;
				}
				if (pstmObj != null) {
					pstmObj.close();
					pstmObj = null;
				}
				if (loginResultObj != null) {
					loginResultObj.close();
					loginResultObj = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
             
        }
        return userLoginObj;
    }	
    
    public String saveSignUpDetails(String firstName, String lastName, String email, String password, String phone, String designation, String company) {    	 
    	PreparedStatement pstmRegObj = null;
    	final int MYSQL_DUPLICATE_PK = 1062; 
    	String signupStatus = null;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.SIGN_UP_INSERT_QUERY);
    		pstmRegObj.setString(1, firstName);
    		pstmRegObj.setString(2, lastName);
    		pstmRegObj.setString(3, email);
    		pstmRegObj.setString(4, password);
    		pstmRegObj.setString(5, phone);
    		pstmRegObj.setString(6, company);
    		pstmRegObj.setString(7, designation);
    		//pstmRegObj.addBatch();   		
    		 
    		pstmRegObj.execute();
    		 
    		dbConn.commit();
        	signupStatus ="S";
    	} 
    	catch(SQLException se){
    	    if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
    	         signupStatus = "E";
    	    }
    	}
    	catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
				signupStatus="F";
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			try {
        		if(dbConn!=null) {
               	 dbConn.close();
               	 dbConn = null;
                }
                if(pstmRegObj!=null) {
                	pstmRegObj.close();
                	pstmRegObj=null;
                }                 
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
    	return signupStatus;
    }
    
}
