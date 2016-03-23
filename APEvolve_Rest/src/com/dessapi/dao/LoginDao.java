package com.dessapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dessapi.bean.SignUpDetailsBean;
import com.dessapi.bean.UserLogin;
import com.dessapi.util.DBUtil;
import com.dessapi.util.SQLConstantUtil;


public class LoginDao {
	Connection dbConn = null;
	public List<String> authenticateUser(String userId, String password) {
		boolean isValidUser = false;
		List<String> loginOutputList = new ArrayList<String>();
        UserLogin user = getUserByUserId(userId);               
        if( user!=null && (userId.equals(user.getUserName()) && password.equals(user.getPassword()))) {
        	loginOutputList.add(0, "S");
        	loginOutputList.add(1, user.getRole());       	 	
        } else {
        	loginOutputList.add(0, "F");
        }
        return loginOutputList;
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
        		userLoginObj.setRole(loginResultObj.getString("role"));
        	}             
        } catch (Exception e) {             
            e.printStackTrace();
        } finally {
        	try {
        		if(dbConn!=null) {
               	 dbConn.close();
               	 dbConn = null;
                }
                if(pstmObj!=null) {
               	 pstmObj.close();
               	 pstmObj=null;
                }
                if(loginResultObj!=null) {
               	 loginResultObj.close();
               	 loginResultObj=null;
                }
			} catch (SQLException se) {
				se.printStackTrace();
			}             
        }
        return userLoginObj;
    }	
    
    public String saveSignUpDetails(SignUpDetailsBean signUpDetails) {    	 
    	PreparedStatement pstmRegObj = null;
    	final int MYSQL_DUPLICATE_PK = 1062; 
    	String signupStatus = null;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.SIGN_UP_INSERT_QUERY);
    		pstmRegObj.setString(1, signUpDetails.getFirstName());
    		pstmRegObj.setString(2, signUpDetails.getLastName());
    		pstmRegObj.setString(3, signUpDetails.getEmail());
    		pstmRegObj.setString(4, signUpDetails.getPassword());
    		pstmRegObj.setString(5, signUpDetails.getPhone());
    		pstmRegObj.setString(6, signUpDetails.getCompany());
    		pstmRegObj.setString(7, signUpDetails.getDesignation());
    		//pstmRegObj.addBatch();   		
    		 
    		pstmRegObj.execute();
    		 
    		dbConn.commit();
        	signupStatus ="S";
    	} 
    	catch(SQLException se){
    	    if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
    	         signupStatus = "E";
    	    }else{
    	    	se.printStackTrace();
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
