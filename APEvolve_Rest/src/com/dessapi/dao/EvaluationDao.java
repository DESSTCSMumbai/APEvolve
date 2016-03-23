package com.dessapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dessapi.bean.CategoryMasterBean;
import com.dessapi.bean.DomainMasterBean;
import com.dessapi.bean.EvalScoreDetails;
import com.dessapi.bean.Features;
import com.dessapi.bean.ProductsBean;
import com.dessapi.bean.TagFeatureMasterBean;
import com.dessapi.util.DBUtil;
import com.dessapi.util.SQLConstantUtil;

public class EvaluationDao {
	Connection dbConn = null;
	public List<Features> getFeatuesList() {
		
		PreparedStatement pstmObj = null;
		ResultSet featureRsObj = null;
		List<Features> featuresList = new ArrayList<Features>();
		Features featureObj = null;
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn
					.prepareStatement(SQLConstantUtil.FEATURES_LIST_QUERY);
			featureRsObj = pstmObj.executeQuery();

			while (featureRsObj.next()) {
				featureObj = new Features();
				featureObj.setCategory(featureRsObj.getString("category"));
				featureObj.setFeatureId(featureRsObj.getString("feature_id"));
				featureObj.setFeatures(featureRsObj.getString("feature"));
				featuresList.add(featureObj);
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
				if (featureRsObj != null) {
					featureRsObj.close();
					featureRsObj = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return featuresList;
	}
	
	public List<ProductsBean> getSelectedProductsList(String [] selectedProds) {
		PreparedStatement pstmObj = null;
    	ResultSet productRsObj = null;
    	List<ProductsBean> productsList = new ArrayList<ProductsBean>();
    	ProductsBean prodObj = null;
		try {
			dbConn = new DBUtil().getConnection();
			
			StringBuilder prodStrBuilder = new StringBuilder();

			for( int i = 0 ; i < selectedProds.length; i++ ) {
			    prodStrBuilder.append("?,");
			}

			String ProdStmt = SQLConstantUtil.SELECTED_PRODUCT_LIST_QUERY +"(" + (prodStrBuilder.deleteCharAt( prodStrBuilder.length() -1 ).toString())+")";
						       	
        	pstmObj = dbConn.prepareStatement(ProdStmt);
        	
        	int index = 1;
        	for( String prodId : selectedProds ) {
        		pstmObj.setObject( index++, prodId); // or whatever it applies 
        	}
        	
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
	
	public boolean saveEvaluationDetails(String compName, Map<String, Integer> featureEvalMap){
		PreparedStatement pstmEvalObj = null;
		PreparedStatement pstmEvalScoreObj = null;
		boolean isDataSaved = false;
    	try {
    		dbConn = new DBUtil().getConnection();
    		dbConn.setAutoCommit(false);
    		
    		pstmEvalObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_FEATURE_INSERT_QUERY);    		
    		for (Map.Entry<String, Integer> entry : featureEvalMap.entrySet()) {
    			pstmEvalObj.setString(1, compName);
    			pstmEvalObj.setString(2, entry.getKey());
    			pstmEvalObj.setInt(3, entry.getValue()); 
    			pstmEvalObj.addBatch();
    		}
    		
    		pstmEvalScoreObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_SCORE_INSERT_QUERY);
    		pstmEvalScoreObj.setString(1, compName);
    		
    		pstmEvalObj.executeBatch();
    		pstmEvalScoreObj.execute();
    		dbConn.commit();
    		isDataSaved = true;
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
                if(pstmEvalObj!=null) {
                	pstmEvalObj.close();
                	pstmEvalObj=null;
                }         
			} catch (SQLException se) {
				se.printStackTrace();
			}			
		}
    	return isDataSaved;
	}
	
	/*public Map<String, ArrayList<String>> getEvaluationScore(String compName) {
		PreparedStatement pstmObj = null;
		ResultSet scoreRs = null;
		//Map<String, ArrayList<String>> scoreMap = new HashMap<String, ArrayList<String>>();
		//List<EvalScoreDetails>  evalScoreList = new ArrayList<EvalScoreDetails>();
		//EvalScoreDetails evalScoreObj = null;
		//int count = 0;
		Map<String, ArrayList<String>> scoreEvalMap = new LinkedHashMap<String, ArrayList<String>>();
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_SCORE_QUERY);
			pstmObj.setString(1, compName);
			scoreRs = pstmObj.executeQuery();

			while (scoreRs.next()) {
				evalScoreObj = new EvalScoreDetails();
				if(scoreRs.getString("cat_id").equals("~")) {
					evalScoreObj.setCategory("Total");
					evalScoreObj.setScore(scoreRs.getString("score"));
					evalScoreList.add(evalScoreObj);
					scoreMap.put(scoreRs.getString("product"),evalScoreList);					
				}
				else {
					//if(count ==0) {
					System.out.println(scoreRs.getString("category"));
						evalScoreObj.setCategory(scoreRs.getString("category"));
						//count++;
					//}					
					evalScoreObj.setScore(scoreRs.getString("score"));
					evalScoreList.add(evalScoreObj);
				}
			}
			String tempProduct = "";
			String tempCat = "";
			
			//List<String> scoreDetailsList = null; 
			while(scoreRs.next() && (!scoreRs.isLast())) {
				String prod = scoreRs.getString("product");
				String cat = scoreRs.getString("cat_id");
				if(! prod.equals(tempProduct)) {					
					ArrayList<String> list = scoreEvalMap.get("Category");
					
					if (list == null)
					{
						scoreEvalMap.put("Category", new ArrayList<String>());
						scoreEvalMap.get("Category").add(scoreRs.getString("product"));
					    list = new ArrayList<String>();
					    scoreEvalMap.put("success", list);
					}
					else {
						scoreEvalMap.get("Category").add(scoreRs.getString("product"));
						//scoreDetailsList = new ArrayList<String>();
					}
					tempProduct = prod;
				}
				if((!cat.equals("~"))) {
				//if((!cat.equals(tempCat)) && (!cat.equals("~"))) {	
					String categ = scoreRs.getString("cat_id");
					ArrayList<String> list = scoreEvalMap.get(categ);
					
					if (list == null)
					{
						scoreEvalMap.put(categ, new ArrayList<String>());							
						scoreEvalMap.get(categ).add(scoreRs.getString("score"));
					    list = new ArrayList<String>();
					    scoreEvalMap.put("success", list);
					}
					else {
						scoreEvalMap.get(categ).add(scoreRs.getString("score"));
						//scoreDetailsList = new ArrayList<String>();
					}
					tempCat = cat;
				}else if(cat.equals("~")) {
					ArrayList<String> list = scoreEvalMap.get("Total");
					
					if (list == null)
					{
						scoreEvalMap.put("Total", new ArrayList<String>());						
						
						scoreEvalMap.get("Total").add(scoreRs.getString("score"));
					    list = new ArrayList<String>();
					    scoreEvalMap.put("success", list);
					}
					else {
						scoreEvalMap.get("Total").add(scoreRs.getString("score"));
						//scoreDetailsList = new ArrayList<String>();
					}
				}
				//
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
				if (scoreRs  != null) {
					scoreRs .close();
					scoreRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return scoreEvalMap;
	}*/
	
	public List<EvalScoreDetails> getEvaluationScore(String compName) {
		PreparedStatement pstmObj = null;
		ResultSet scoreRs = null;
		
		List<EvalScoreDetails>  evalScoreList = new ArrayList<EvalScoreDetails>();
		EvalScoreDetails evalScoreObj = null;
				
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_SCORE_SIMPLE_QUERY);
			pstmObj.setString(1, compName);
			scoreRs = pstmObj.executeQuery();

			
			//List<String> scoreDetailsList = null; 
			while(scoreRs.next()) {
				evalScoreObj = new EvalScoreDetails();
				evalScoreObj.setCategory(scoreRs.getString("category"));
				evalScoreObj.setProduct(scoreRs.getString("product"));
				evalScoreObj.setScore(scoreRs.getString("score"));
				evalScoreList.add(evalScoreObj);
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
				if (scoreRs  != null) {
					scoreRs .close();
					scoreRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return evalScoreList;
	}
	
	/*public Map<String, ArrayList<String>> getEvaluationCatScore(String compName, String category) {
		PreparedStatement pstmObj = null;
		ResultSet scoreRs = null;

		Map<String, ArrayList<String>> scoreEvalMap = new LinkedHashMap<String, ArrayList<String>>();
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_SCORE_CAT_DETAILS_QUERY);
			pstmObj.setString(1, compName);
			pstmObj.setString(2, category);
			
			scoreRs = pstmObj.executeQuery();

			String tempProd = "";
			String tempCat = "";
			
			//List<String> scoreDetailsList = null; 
			while(scoreRs.next()) {
				String prod = scoreRs.getString("product_id");
				String cat = scoreRs.getString("feature_id");
				
				if(! prod.equals(tempProd)) {				
					ArrayList<String> list = scoreEvalMap.get("Feature");
					
					if (list == null)
					{
						scoreEvalMap.put("Feature", new ArrayList<String>());
						scoreEvalMap.get("Feature").add(scoreRs.getString("product"));
					}
					else {
						scoreEvalMap.get("Feature").add(scoreRs.getString("product"));
					}
					tempProd = prod;
				}
				//if(!cat.equals(tempCat)) {	
					String categ = scoreRs.getString("feature");
					ArrayList<String> list = scoreEvalMap.get(categ);
					String relData = null;
					if (list == null)
					{
						scoreEvalMap.put(categ, new ArrayList<String>());												
						//scoreEvalMap.get(categ).add(scoreRs.getString("score_comment"));
						if(scoreRs.getString("relevance").contains("Yes")) {
							//relData = scoreRs.getString("relevance").replaceAll("Yes", "<i class='fa fa-check fa-lg yes'></i>");
							relData = scoreRs.getString("relevance");
						}
						else if(scoreRs.getString("relevance").contains("No")) {
							//relData = scoreRs.getString("relevance").replaceAll("No", "<i class='fa fa-times fa-lg no'></i>");
							relData = scoreRs.getString("relevance");
						}
						
						scoreEvalMap.get(categ).add(relData);
					}
					else {						
						
						if(scoreRs.getString("relevance").contains("Yes")) {
							//relData = scoreRs.getString("relevance").replaceAll("Yes", "<i class='fa fa-check fa-lg yes'></i>");
							relData = scoreRs.getString("relevance");
						}
						else if(scoreRs.getString("relevance").contains("No")) {
//							relData = scoreRs.getString("relevance").replaceAll("No", "<i class='fa fa-times fa-lg no'></i>");
							relData = scoreRs.getString("relevance");
						}
						
						scoreEvalMap.get(categ).add(relData);
					}
					//tempCat = cat;
				//}
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
				if (scoreRs  != null) {
					scoreRs .close();
					scoreRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return scoreEvalMap;
	}*/
	
	public List<EvalScoreDetails> getEvaluationCatScore(String compName, String category) {
		PreparedStatement pstmObj = null;
		ResultSet scoreRs = null;

		List<EvalScoreDetails>  evalScoreList = new ArrayList<EvalScoreDetails>();
		EvalScoreDetails evalScoreObj = null;
				
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_SCORE_CAT_DETAILS_SIMPLE_QUERY);
			pstmObj.setString(1, compName);
			pstmObj.setString(2, category);
			scoreRs = pstmObj.executeQuery();

			
			//List<String> scoreDetailsList = null; 
			while(scoreRs.next()) {
				evalScoreObj = new EvalScoreDetails();
				evalScoreObj.setFeature(scoreRs.getString("feature"));
				evalScoreObj.setProduct(scoreRs.getString("product"));
				evalScoreObj.setScore(scoreRs.getString("relevance"));
				evalScoreList.add(evalScoreObj);
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
				if (scoreRs  != null) {
					scoreRs .close();
					scoreRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return evalScoreList;
	}
	
	public List<String[]> getEvaluationCSVData(String compName) {
		PreparedStatement pstmObj = null;
		ResultSet scoreRs = null;
		List<String[]> evalData = new ArrayList<String[]>();
		
		List<String> scoreData = new ArrayList<String>();

		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_CSV_RPT_QUERY);
			pstmObj.setString(1, compName);
			scoreRs = pstmObj.executeQuery();
			
			ResultSetMetaData metaData = scoreRs.getMetaData();
			int count = metaData.getColumnCount();
			String [] emptyLine = null;
			String tempProd = "";
			int idx = 0;						 
			while(scoreRs.next()) {
				String prod = scoreRs.getString("product_id");
				
				if((!prod.equals(tempProd)) && (idx!=0) ) {
					emptyLine = new String[count];
					Arrays.fill(emptyLine,"------");
					evalData.add(emptyLine);
				}
				else if (idx==0){
					for(int colCnt = 2; colCnt <=count; colCnt++){
						
						scoreData.add(metaData.getColumnName(colCnt).toUpperCase());
					}
					evalData.add(scoreData.toArray(new String[scoreData.size()]));
					scoreData.clear();
				}
							
				scoreData.add(scoreRs.getString("product"));
				scoreData.add(scoreRs.getString("category"));
				scoreData.add(scoreRs.getString("feature"));
				scoreData.add(scoreRs.getString("score"));
				scoreData.add(scoreRs.getString("comment"));			
				
				evalData.add(scoreData.toArray(new String[scoreData.size()]));
				//System.out.println(scoreData.toArray(new String[scoreData.size()]));
				scoreData.clear();
				tempProd=prod;
				idx++;								
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
				if (scoreRs  != null) {
					scoreRs .close();
					scoreRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return evalData;
	}
	
	public Map<String, Map<String, ArrayList<String>>> getEvaluationScoreDetails(String compName) {
		PreparedStatement pstmObj = null;
		ResultSet scoreRs = null;
		 
		//List<EvalScoreDetails>  evalScoreList = new ArrayList<EvalScoreDetails>();
		EvalScoreDetails evalScoreObj = null;
		int count = 0;
		Map<String, Map<String, ArrayList<String>>> scoreEvalMap = new LinkedHashMap<String, Map<String, ArrayList<String>>>();
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.EVAL_SCORE_DETAILS_QUERY);
			pstmObj.setString(1, compName);
			scoreRs = pstmObj.executeQuery();
			
			String tempProduct = "";
			String tempCat = "";
			
			List<String> scoreDetailsList = null;
			while(scoreRs.next()){
				String prod = scoreRs.getString("product");
				
				if(! prod.equals(tempProduct)) {
					
					Map<String, ArrayList<String>> catMap = scoreEvalMap.get("category");
					 
					if(catMap == null) {
						scoreEvalMap.put("category", new HashMap<String, ArrayList<String>>());
						catMap.put("features", new ArrayList<String>());
						ArrayList<String> flist = catMap.get("features");
						
						
						if(flist == null){ 
							
							
							
						}
							
							
						
						//featureMap = new HashMap<String, ArrayList<String>>().put("features", new ArrayList<String>());
						//featureMap.put("features", new ArrayList<String>());
						
						//scoreEvalMap.get("category").get("features").
						catMap.get("features").add(scoreRs.getString("product"));
						//scoreEvalMap.put("category", featureMap);
					}
					//ArrayList<String> list = scoreEvalMap.get("category");
					
					
					/*if (list == null)
					{
						scoreEvalMap.put("category", new ArrayList<String>());
						scoreEvalMap.get("category").add(scoreRs.getString("product"));
					}*/
					else {
						scoreEvalMap.get("category").get("features").add(scoreRs.getString("product"));
						//scoreEvalMap.get("category").add(scoreRs.getString("product"));
					}
					tempProduct = prod;
				}
			}
			System.out.println("-----");
			for (Map.Entry<String, Map<String, ArrayList<String>>> letterEntry : scoreEvalMap.entrySet()) {
			    String letter = letterEntry.getKey();
			    System.out.print(":"+letter+":");
			    for (Map.Entry<String, ArrayList<String>> nameEntry : letterEntry.getValue().entrySet()) {
			    	String str = nameEntry.getKey();
			    	System.out.print(str+":");
			    	ArrayList<String> arstr = nameEntry.getValue();
			    	for(String aString : arstr){
			            System.out.println(":key : " + str + " value : " + aString);
			        }			         
			    }
			}
			/*while(scoreRs.next() && (!scoreRs.isLast())) {				
				String prod = scoreRs.getString("product");
				String cat = scoreRs.getString("cat_id");
				if(! prod.equals(tempProduct)) {					
					ArrayList<String> list = scoreEvalMap.get("category");
					
					if (list == null)
					{
						scoreEvalMap.put("category", new ArrayList<String>());
						scoreEvalMap.get("category").add(scoreRs.getString("product"));
					}
					else {
						scoreEvalMap.get("category").add(scoreRs.getString("product"));
					}
					tempProduct = prod;
				}
				if((!cat.equals(tempCat)) && (!cat.equals("~"))) {	
					String categ = scoreRs.getString("category");
					ArrayList<String> list = scoreEvalMap.get(categ);
					
					if (list == null)
					{
						scoreEvalMap.put(categ, new ArrayList<String>());												
						scoreEvalMap.get(categ).add(scoreRs.getString("score"));
					}
					else {
						scoreEvalMap.get(categ).add(scoreRs.getString("score"));
					}
					tempCat = cat;
				}else if(cat.equals("~")) {
					ArrayList<String> list = scoreEvalMap.get("Total");
					
					if (list == null)
					{
						scoreEvalMap.put("Total", new ArrayList<String>());												
						scoreEvalMap.get("Total").add(scoreRs.getString("score"));					    
					}
					else {
						scoreEvalMap.get("Total").add(scoreRs.getString("score"));						
					}
				}
			}*/				   
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
				if (scoreRs  != null) {
					scoreRs .close();
					scoreRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return scoreEvalMap;
	}
	
	public List<String> getEvaluationHistory(String prodType, String role, String username){
		PreparedStatement pstmObj = null;
		ResultSet evalHistRs = null;
		List<String> evalList = new ArrayList<String>();
		String finalQ = SQLConstantUtil.EVAL_HISTORY_QUERY;
		if(!role.equalsIgnoreCase("admin")) {
			finalQ = finalQ + " and es.registered_by = ? ";
		}
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(finalQ);
			pstmObj.setString(1, prodType);
			if(!role.equalsIgnoreCase("admin")) {
				pstmObj.setString(2, username);
			}
			evalHistRs = pstmObj.executeQuery();
			
			ResultSetMetaData metaData = evalHistRs.getMetaData();
			int count = metaData.getColumnCount();
			
			while(evalHistRs.next()) {											
				evalList.add(evalHistRs.getString("eval_id"));																						
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
				if (evalHistRs  != null) {
					evalHistRs .close();
					evalHistRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return evalList;
	}
	
	public ArrayList<ProductsBean> getProduct (String prod_type)
	{
		 ArrayList<ProductsBean> products = new ArrayList<ProductsBean>();
		PreparedStatement pstmObj = null;
		ResultSet productRs = null;
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.PRODUCTTYPE_PRODUCT_LIST_QUERY);
			pstmObj.setString(1, prod_type);
		    productRs = pstmObj.executeQuery();
			while(productRs.next())
			{
				ProductsBean productObj=new ProductsBean();
				productObj.setProductId(productRs.getString("product_id"));
				productObj.setProductName(productRs.getString("product_name"));
				products.add(productObj);
			}
		}catch (Exception e) {
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
				if (productRs  != null) {
					productRs .close();
					productRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return products;
	}
	
	public   ArrayList<DomainMasterBean> getDomain ()
	{
		 ArrayList<DomainMasterBean> domains = new ArrayList<DomainMasterBean>();
		PreparedStatement pstmObj = null;
		ResultSet domainRs = null;
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.DOMAIN_LIST_QUERY);
			domainRs = pstmObj.executeQuery();
			while(domainRs.next())
			{
				DomainMasterBean domainObj=new DomainMasterBean();
				domainObj.setDomainId(domainRs.getString("domain_id"));
				domainObj.setDomainName(domainRs.getString("domain_name"));
				domains.add(domainObj);
			}
		}catch (Exception e) {
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
				if (domainRs  != null) {
					domainRs .close();
					domainRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return domains;
	}
	
	public Map<String,ArrayList> getTagFeature_FeatureList(String domainId) {
		PreparedStatement pstmObj = null;
    	ResultSet tagfeaturesRsObj=null ,featuresRsObj= null;
    	List<TagFeatureMasterBean> tagfeaturesList = new ArrayList<TagFeatureMasterBean>();
    	TagFeatureMasterBean tagfeaturesObj = null;
    	List<Features> featuresList = new ArrayList<Features>();
    	Features featuresObj = null;
    	Map<String,ArrayList> result=new  LinkedHashMap<String,ArrayList>();
		try {
			dbConn = new DBUtil().getConnection();
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.TAG_FEATURE_LIST_QUERY);
        	pstmObj.setString(1,domainId);
        	tagfeaturesRsObj = pstmObj.executeQuery();
       
        	while(tagfeaturesRsObj.next()) {        	
        		tagfeaturesObj = new  TagFeatureMasterBean();
        		tagfeaturesObj.setTagId(tagfeaturesRsObj.getString("tag_id"));
        		tagfeaturesObj.setTagName(tagfeaturesRsObj.getString("tag_name"));
        		tagfeaturesObj.setFeatureId(tagfeaturesRsObj.getString("feature_id"));
        		tagfeaturesObj.setProductType(tagfeaturesRsObj.getString("prod_type"));
        		tagfeaturesList.add(tagfeaturesObj);
        	}
        	pstmObj = dbConn.prepareStatement(SQLConstantUtil.FEATURE_LIST_QUERY);
        	pstmObj.setString(1,domainId);
        	featuresRsObj = pstmObj.executeQuery();
        	
        	while(featuresRsObj.next()) {        	
        		featuresObj = new  Features();
        		featuresObj.setCategory(featuresRsObj.getString("category"));
        		featuresObj.setFeatureId(featuresRsObj.getString("feature_id"));
        		featuresObj.setFeatures(featuresRsObj.getString("feature"));
        		featuresList.add(featuresObj);
		    }
        	
			result.put("features",(ArrayList) featuresList);  
			result.put("tag features",(ArrayList) tagfeaturesList);  

       }
        	catch (Exception e) {
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
                if(tagfeaturesRsObj!=null) {
                	tagfeaturesRsObj.close();
                	tagfeaturesRsObj=null;
                }
                if(featuresRsObj!=null) {
                	featuresRsObj.close();
                	featuresRsObj=null;
                }
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result; 
	}
	
	public   ArrayList<CategoryMasterBean> getCategory(String prod_type)
	{
		ArrayList<CategoryMasterBean> category = new ArrayList<CategoryMasterBean>();
		PreparedStatement pstmObj = null;
		ResultSet categoryRs = null;
		try {
			dbConn = new DBUtil().getConnection();
			pstmObj = dbConn.prepareStatement(SQLConstantUtil.CATEGORY_LIST_QUERY_1);
			pstmObj.setString(1, prod_type);
			categoryRs = pstmObj.executeQuery();
			while(categoryRs.next())
			{
				CategoryMasterBean categoryObj=new CategoryMasterBean();
				categoryObj.setCategoryId(categoryRs.getString("category_id"));
				categoryObj.setCategoryName(categoryRs.getString("category"));
				category.add(categoryObj);
			}
		}catch (Exception e) {
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
				if (categoryRs  != null) {
					categoryRs .close();
					categoryRs  = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return category;
	}
	
}
