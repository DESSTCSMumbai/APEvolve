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
	
	public List<ProductsBean> getProductAndDomainList() {
		PreparedStatement pstmObj = null;
    	ResultSet productRsObj = null;
    	List<ProductsBean> productsList = new ArrayList<ProductsBean>();
    	ProductsBean prodObj = null;
		try {
			dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.PRODUCTTYPE_PRODUCT_LIST_QUERY);
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
	
	public boolean saveEvalRegisterationDetails(String compname, String[] products){
		PreparedStatement pstmRegObj = null;
		PreparedStatement pstmRegObj1 = null;
		final int MYSQL_DUPLICATE_PK = 1062; 
		boolean isDataSaved = false;
		
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		pstmRegObj = dbConn.prepareStatement(SQLConstantUtil.REGISTERATION_SESSION_INSERT_QUERY);
    		pstmRegObj.setString(1, compname);
    		/*pstmRegObj.setInt(2, lowVal);
    		pstmRegObj.setInt(3, mediumVal);
    		pstmRegObj.setInt(4, highVal);*/
    		//pstmRegObj.addBatch();
        	
    		pstmRegObj1 = dbConn.prepareStatement(SQLConstantUtil.REGISTERATION_PRODUCTS_INSERT_QUERY);
    		for(int arrLen=0; arrLen<products.length;arrLen++){    			
    			pstmRegObj1.setString(1, compname);
        		pstmRegObj1.setString(2, products[arrLen]);
        		pstmRegObj1.addBatch();
    		}
    		System.out.println(isDataSaved);
    		
    		pstmRegObj.execute();    		
    		pstmRegObj1.executeBatch();
    		
    		dbConn.commit();
        	isDataSaved = true;         	
    	} 
    	catch(SQLException se){
    	    if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
    	    	isDataSaved = false;
    	    }
    	    else {
    	    	se.printStackTrace();
    	    }
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
	
	public boolean checkSignUpEmail(String email) {
    	PreparedStatement pstmObj = null;
    	ResultSet loginResultObj = null;
    	boolean isemailUsed = false;
        try {
        	dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.EMAIL_CHECK_QUERY);
        	pstmObj.setString(1, email.toLowerCase());
        	loginResultObj = pstmObj.executeQuery();
        	if(loginResultObj.next()) {
        		isemailUsed = true;
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
        return isemailUsed;
    }
	
	public boolean UpdateTagCount(String tagid) {
		PreparedStatement pstmObj=null;
    	
    	boolean isDataSaved = false;
		try {
			dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.UPDATE_COUNT_QUERY);
        	pstmObj.setString(1, tagid);
        	int flag=pstmObj.executeUpdate();
        	if(flag!=0)
    		isDataSaved = true;
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
   
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return isDataSaved; 
	}
	
}
