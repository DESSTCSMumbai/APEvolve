package com.dessapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dessapi.bean.ProductsBean;
import com.dessapi.bean.UserLogin;
import com.dessapi.util.DBUtil;
import com.dessapi.util.SQLConstantUtil;

public class EvaluationRegisterationDao {
	Connection dbConn = null;
	
	public List<ProductsBean> getProductsList() {
		PreparedStatement pstmObj = null;
    	ResultSet productRsObj = null;
    	List<ProductsBean> productsList = new ArrayList<ProductsBean>();
    	ProductsBean prodObj = null;
		try {
			dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.PRODUCT_LIST_QUERY);
        	productRsObj = pstmObj.executeQuery();
        	
        	while(productRsObj.next()) {        	
        		prodObj = new ProductsBean();
        		prodObj.setProductId(productRsObj.getString("product_id"));
        		prodObj.setProductName(productRsObj.getString("product_name"));
        		productsList.add(prodObj);
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
                if(productRsObj!=null) {
                	productRsObj.close();
                	productRsObj=null;
                }
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return productsList; 
	}
	
	public Map<String, String> getCategoryList() {
		PreparedStatement pstmObj = null;
    	ResultSet productRsObj = null;
    	Map<String,String> catMap = new HashMap<String, String>();
    	
		try {
			dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.CATEGORY_LIST_QUERY);
        	productRsObj = pstmObj.executeQuery();
        	
        	while(productRsObj.next()) {        	
        		catMap.put(productRsObj.getString("category_id"), productRsObj.getString("category"));
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
                if(productRsObj!=null) {
                	productRsObj.close();
                	productRsObj=null;
                }
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return catMap; 
	}
	
	public void saveEvalRegisterationDetails(String compname, String[] products, int lowVal, int mediumVal, int highVal){
		PreparedStatement pstmRegObj = null;
		PreparedStatement pstmRegObj1 = null;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.REGISTERATION_SESSION_INSERT_QUERY);
    		pstmRegObj.setString(1, compname);
    		pstmRegObj.setInt(2, lowVal);
    		pstmRegObj.setInt(3, mediumVal);
    		pstmRegObj.setInt(4, highVal);
    		//pstmRegObj.addBatch();
        	
    		pstmRegObj1 = dbConn.prepareStatement(SQLConstantUtil.REGISTERATION_PRODUCTS_INSERT_QUERY);
    		for(int arrLen=0; arrLen<products.length;arrLen++){
    			//System.out.println(":::"+products[arrLen]);
    			pstmRegObj1.setString(1, compname);
        		pstmRegObj1.setString(2, products[arrLen]);
        		pstmRegObj1.addBatch();
    		}
    		
    		pstmRegObj.execute();
    		pstmRegObj1.executeBatch();
    		dbConn.commit();
        	
    	} catch (Exception e) {
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
	}
	
	public boolean checkEvalCompName(String companyName) {    	 
    	PreparedStatement pstmObj = null;
    	ResultSet loginResultObj = null;
    	boolean isCompanyNameUsed = false;
        try {
        	dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.COMPANYNAME_CHECK_QUERY);
        	pstmObj.setString(1, companyName);
        	loginResultObj = pstmObj.executeQuery();
        	if(loginResultObj.next()) {
        		isCompanyNameUsed = true;
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
        return isCompanyNameUsed;
    }	
	
}
