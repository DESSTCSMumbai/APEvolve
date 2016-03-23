package com.dessapi.restservices;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dessapi.bean.CategoryMasterBean;
import com.dessapi.bean.DashboardInputBean;
import com.dessapi.bean.DomainMasterBean;
import com.dessapi.bean.EvalHistoryBean;
import com.dessapi.bean.EvalScoreDetails;
import com.dessapi.bean.ProductsBean;
import com.dessapi.dao.EvaluationDao;
import com.dessapi.dao.EvaluationRegisterationDao;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

@Path("/evaluation")
public class EvaluationService {
	@POST
	@Path("/history")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvaluationHistory(EvalHistoryBean evalHistory) {
		String evalListJson = null;
		try {
			
			ArrayList<String> histList = (ArrayList<String>) new EvaluationDao().getEvaluationHistory(evalHistory.getProductType(), evalHistory.getRole(), evalHistory.getUsername());
			HashMap<String, ArrayList> evalmapList =  new HashMap<String, ArrayList>();
			evalmapList.put("evalHistory", histList);
			
			evalListJson = new Gson().toJson(evalmapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(evalListJson).build();
	}
	
	@GET
	@Path("productsanddomains/{producttype}")
	public Response getProductDomainList(@PathParam("producttype") String prod_type) {
		String resultJson=null;
		try {
			ArrayList<ProductsBean> productsList=new EvaluationDao().getProduct(prod_type);
			ArrayList<DomainMasterBean> domainsList=new EvaluationDao().getDomain();
			Map<String,ArrayList> result=new  LinkedHashMap<String,ArrayList>();
			result.put("products",productsList);  
			result.put("domains",domainsList);  
			resultJson = new Gson().toJson(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(resultJson).build();	
	}
	
	@GET
	@Path("/productsandcategories/{producttype}")
	public Response getProduct_CategoryList(@PathParam("producttype") String prod_type) {
		String resultJson=null;
		try {
			List<ProductsBean> productsList=new EvaluationDao().getProduct(prod_type);
			List<CategoryMasterBean> categoryList=new EvaluationDao().getCategory(prod_type);
			Map<String,ArrayList> result=new  LinkedHashMap<String,ArrayList>();
			result.put("products",(ArrayList) productsList);  
			result.put("categories",(ArrayList) categoryList);  
			resultJson = new Gson().toJson(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(resultJson).build();	
	}
	
	@POST
	@Path("/dashboard")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDashboard(DashboardInputBean dashboardInp) {
		String dataJson = null;
		try {
			List<EvalScoreDetails> evalScore = null;
			if (dashboardInp.getCompName() != null) {
				evalScore = new EvaluationDao().getEvaluationScore(dashboardInp.getCompName());
			}
			//dataJson = scoreMap.toString();
			dataJson = new Gson().toJson(evalScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(dataJson).build();
	}

	@POST
	@Path("/catdashboard")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//public Response getDashboard(@PathParam("compName") String compName, @PathParam("categoryId") String categoryId) {
	public Response getCategoryDashboard(DashboardInputBean dashboardInp) {
		String dataJson = null;
		try {
			List<EvalScoreDetails> evalScore = null;
			if (dashboardInp.getCatId() != null && dashboardInp.getCompName() != null) {
				evalScore = new EvaluationDao().getEvaluationCatScore(dashboardInp.getCompName(), dashboardInp.getCatId());
			}
			
			dataJson = new Gson().toJson(evalScore);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Response.status(200).entity(dataJson).build();
	}
	
	@GET
	@Path("/featureevalload/{domainid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response featureEvalLoad(@PathParam("domainid") String domainId) {
		String resultJson=null;
		try {
			//List<Features> featuresList=new EvaluationRegisterationDao().getFeatureList(domainId);
			//List<TagFeatureMasterBean> tagFeaturesList=new EvaluationRegisterationDao().getTagFeatureList(domainId);
			Map<String,ArrayList> result=new  EvaluationDao().getTagFeature_FeatureList(domainId);
			//result.put("features",(ArrayList) featuresList);  
			//result.put("tag features",(ArrayList) tagFeaturesList);  
			resultJson = new Gson().toJson(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(resultJson).build();	
	}
	
	@GET
	@Path("/csvreport/{evalId}")
	//@Produces({"text/csv"})
	

	public Response generateCSVReport(@PathParam("evalId") String evalId, @Context HttpServletResponse response) {
		String resultJson=null;
		CSVWriter writer =null;	
		List<String[]> tableExport = null;
		//response.setContentType("application/csv");
		try {											
			tableExport = new EvaluationDao().getEvaluationCSVData(evalId);		
			ServletOutputStream out=response.getOutputStream();  
			response.setContentType("text/csv");
		    response.setHeader("Content-Disposition","filename=evaluation_report.csv");
		    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out));
		    writer = new CSVWriter(buff);
		    //writer.write
		    System.out.println();
		    writer.writeAll(tableExport);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.ok().build();
	}
}
