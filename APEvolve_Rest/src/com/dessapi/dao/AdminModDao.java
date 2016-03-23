package com.dessapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dessapi.bean.CategoryMasterBean;
import com.dessapi.bean.FeatureMasterBean;
import com.dessapi.bean.ProductMasterBean;
import com.dessapi.bean.SignUpDetailsBean;
import com.dessapi.util.DBUtil;
import com.dessapi.util.SQLConstantUtil;

public class AdminModDao {
	Connection dbConn = null;
	public boolean addProductDetails(ProductMasterBean prod){
		PreparedStatement pstmRegObj = null;
		
		boolean isDataSaved = false;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.PRODUCT_MASTER_INSERT_QUERY);
    		pstmRegObj.setString(1, prod.getProductName());
    		pstmRegObj.setString(2, prod.getProductVersion());
    		pstmRegObj.setString(3, prod.getProductType());    		    	
    		
    		pstmRegObj.execute();
    		
    		dbConn.commit();
        	isDataSaved = true;
    	}
    	
    	catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
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
    	return isDataSaved;
	}
	
	public boolean addCategoryDetails(CategoryMasterBean cat){
		PreparedStatement pstmRegObj = null;
		
		boolean isDataSaved = false;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.CATEGORY_MASTER_INSERT_QUERY);
    		pstmRegObj.setString(1, cat.getCategoryName());
    		pstmRegObj.setString(2, cat.getProdType() );    		    		    	
    		
    		pstmRegObj.execute();
    		
    		dbConn.commit();
        	isDataSaved = true;
    	}
    	
    	catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
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
    	return isDataSaved;
	}
	
	public boolean addFeatureDetails(ArrayList<FeatureMasterBean> feature){
		PreparedStatement pstmRegObj = null;
		PreparedStatement pstmRegObj1 = null;
		boolean isDataSaved = false;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		if(feature.size()>0) {
    			pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.FEATURE_MASTER_INSERT_QUERY);        		
        		pstmRegObj.setString(1, feature.get(0).getMstCategoryId());
        		pstmRegObj.setString(2, feature.get(0).getFeature() );
    		}
    		pstmRegObj1 = dbConn.prepareStatement(SQLConstantUtil.FEATURE_RATING_INSERT_QUERY);
    		for(FeatureMasterBean featureDetailsObj : feature) {
    			pstmRegObj1.setString(1, featureDetailsObj.getMstProductId());
    			pstmRegObj1.setInt(2, featureDetailsObj.getRating());
    			pstmRegObj1.setString(3, featureDetailsObj.getComment());
    			pstmRegObj1.setString(4, featureDetailsObj.getReference());
    			pstmRegObj1.setString(5, featureDetailsObj.getRelevance());
    			pstmRegObj1.addBatch();
    		}    		
    		pstmRegObj.execute();
    		pstmRegObj1.executeBatch();
    		
    		dbConn.commit();
        	isDataSaved = true;
    	}
    	
    	catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
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
                if(pstmRegObj1!=null) {
                	pstmRegObj1.close();
                	pstmRegObj1=null;
                } 
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
    	return isDataSaved;
	}
	
	public List<SignUpDetailsBean> getRegisteredUserList() {
		PreparedStatement pstmObj = null;
    	ResultSet userlistRsObj = null;
    	List<SignUpDetailsBean> userList = new ArrayList<SignUpDetailsBean>();
    	
		try {
			dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.SIGNUP_USERSLIST_QUERY);
        	userlistRsObj = pstmObj.executeQuery();
        	SignUpDetailsBean userDet;
        	while(userlistRsObj.next()) {        	
        		userDet = new SignUpDetailsBean();
        		userDet.setCompany(userlistRsObj.getString("company"));
        		userDet.setDesignation(userlistRsObj.getString("designation"));
        		userDet.setEmail(userlistRsObj.getString("email"));
        		userDet.setFirstName(userlistRsObj.getString("first_name"));
        		userDet.setLastName(userlistRsObj.getString("last_name"));
        		userDet.setPhone(userlistRsObj.getString("phone"));
        		userDet.setSignupDate(userlistRsObj.getString("RegDate"));
        		userList.add(userDet);        		
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
                if(userlistRsObj!=null) {
                	userlistRsObj.close();
                	userlistRsObj=null;
                }
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return userList; 
	}
	
	public boolean submitApproval(String email) {
		PreparedStatement pstmObj=null;
		PreparedStatement pstmObj1 = null;
		final int MYSQL_DUPLICATE_PK = 1062; 
    	boolean isDataSaved = false;
		try {
			dbConn = new DBUtil().getConnection();
			dbConn.setAutoCommit(false);
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.UPDATE_USER_STATUS);        	
        	pstmObj.setString(1, email);
        	int flag=pstmObj.executeUpdate();
        	
        	pstmObj1 = dbConn.prepareStatement(SQLConstantUtil.INSERT_APPROVED_LOGIN_DETAILS);        	
        	pstmObj1.setString(1, email);
        	int flag1=pstmObj1.executeUpdate();
        	
        	if(flag!=0 & flag1!=0) {
        		dbConn.commit();
        		isDataSaved = true; 
    		}
		} catch(SQLException se){
    	    if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
    	    	isDataSaved = false;
    	    }
    	}
    	catch (Exception e) {
			e.printStackTrace();
			try {
				dbConn.rollback();
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
			isDataSaved = false;
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
                if(pstmObj1!=null) {
                	pstmObj1.close();
                	pstmObj1=null;
                } 
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return isDataSaved; 
	}

}
