package com.dessapi.restservices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.dessapi.bean.CategoryMasterBean;
import com.dessapi.bean.FeatureMasterBean;
import com.dessapi.bean.Features;
import com.dessapi.bean.ProductMasterBean;
import com.dessapi.bean.ProductsBean;
import com.dessapi.bean.SignUpDetailsBean;
import com.dessapi.dao.AdminModDao;
import com.dessapi.dao.EvaluationDao;
import com.dessapi.dao.EvaluationRegisterationDao;
import com.google.gson.Gson;

@Path("/masterdata")
public class MasterDataService {
	@GET
	@Path("/products")
	public Response getProductList() {
		String productListJson = null;
		try {
			List<ProductsBean> productsList = new EvaluationRegisterationDao().getProductsList();
			productListJson = new Gson().toJson(productsList);
			//System.out.println(productListJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(productListJson).build();	
	}
	
	/*@GET
	@Path("/productanddomain")
	public Response getProductAndDomainList() {
		String productListJson = null;
		try {
			List<ProductsBean> productsList = new EvaluationRegisterationDao().getProductAndDomainList();
			productListJson = new Gson().toJson(productsList);
			//System.out.println(productListJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(productListJson).build();	
	}*/
	
	@GET
	@Path("/features")
	
	public Response getCategoryFeatures() {
		String featuresListJson = null;
		try {
			List<Features> featuresList = new EvaluationDao().getFeatuesList();
			featuresListJson = new Gson().toJson(featuresList);
			//System.out.println(productListJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(featuresListJson).build();	
	}
	
	@POST
	@Path("/addproduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(ProductMasterBean prodMasterObj){
		JSONObject jOutObj = new JSONObject();
		try {
			boolean saveStatus = new AdminModDao().addProductDetails(prodMasterObj);
	
			if (saveStatus) {
				jOutObj.put("prodSaveCode", "S");
				jOutObj.put("message", "Product added Successfully!");
			} else {
				jOutObj.put("prodSaveCode", "F");
				jOutObj.put("message", "Failed to add new product!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Response.status(200).entity(jOutObj.toString()).build();
	}
	
	@POST
	@Path("/addcategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(CategoryMasterBean catMasterObj){
		JSONObject jOutObj = new JSONObject();
		try {
			boolean saveStatus = new AdminModDao().addCategoryDetails(catMasterObj);
	
			if (saveStatus) {
				jOutObj.put("catSaveCode", "S");
				jOutObj.put("message", "Category added Successfully!");
			} else {
				jOutObj.put("catSaveCode", "F");
				jOutObj.put("message", "Failed to add new category!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Response.status(200).entity(jOutObj.toString()).build();
	}
	
	@POST
	@Path("/addfeature")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFeature(ArrayList<FeatureMasterBean> feaMasterObj){
		JSONObject jOutObj = new JSONObject();
		try {
			boolean saveStatus = new AdminModDao().addFeatureDetails(feaMasterObj);
	
			if (saveStatus) {
				jOutObj.put("feaSaveCode", "S");
				jOutObj.put("message", "Feature added Successfully!");
			} else {
				jOutObj.put("feaSaveCode", "F");
				jOutObj.put("message", "Failed to add new feature!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Response.status(200).entity(jOutObj.toString()).build();
	}	
	
	@GET
	@Path("/registeredusers")	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegisteredUserList(){
		JSONObject jOutObj = new JSONObject();
		String registeredUsers = null;
		try {
			List<SignUpDetailsBean> userList = new AdminModDao().getRegisteredUserList();
			registeredUsers = new Gson().toJson(userList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Response.status(200).entity(registeredUsers).build();
	}
	
	@POST
	@Path("/approveregistereduser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userApproval(SignUpDetailsBean user) {
		JSONObject regOutObj = new JSONObject();
		try {
			boolean saveStatus = new AdminModDao().submitApproval(user.getEmail());
			
			if (saveStatus) {
				regOutObj.put("regSaveCode", "S");
				regOutObj.put("message", "User approval Successful!");
			} else {
				regOutObj.put("regSaveCode", "F");
				regOutObj.put("message", "User approval Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(regOutObj.toString()).build();
	}
	
	@POST
	@Path("/savetagcount/{tagid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveTagCount(@PathParam("tagid") String tagid) {
		JSONObject regOutObj = new JSONObject();
		try {
			boolean saveStatus = new EvaluationRegisterationDao().UpdateTagCount(tagid); 
			System.out.println(saveStatus);
			if (saveStatus) {
				regOutObj.put("regSaveCode", "S");
				regOutObj.put("message", "Updation Successful!");
			} else {
				regOutObj.put("regSaveCode", "F");
				regOutObj.put("message", "Updation Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(regOutObj.toString()).build();
	}
	
	@GET
	@Path("/checkEmail/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkCompName(@PathParam("email") String email) {
		JSONObject emailStatusOutObj = new JSONObject();
		try {
			boolean compExist = new EvaluationRegisterationDao().checkSignUpEmail(email);
			if (compExist) {
				emailStatusOutObj.put("emailExistCode", "1");
				emailStatusOutObj.put("message", "Email already used!");
			} else {
				emailStatusOutObj.put("emailExistCode", "0");
				emailStatusOutObj.put("message", "Correct!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(emailStatusOutObj.toString()).build();
	}
}
