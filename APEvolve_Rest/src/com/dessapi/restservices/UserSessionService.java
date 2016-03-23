package com.dessapi.restservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

import com.dessapi.bean.DashboardInputBean;
import com.dessapi.bean.EvalScoreDetails;
import com.dessapi.bean.EvalSessionRegDetails;
import com.dessapi.bean.SaveEvaluationBean;
import com.dessapi.bean.SignUpDetailsBean;
import com.dessapi.bean.UserLogin;
import com.dessapi.dao.EvaluationDao;
import com.dessapi.dao.EvaluationRegisterationDao;
import com.dessapi.dao.LoginDao;
import com.google.gson.Gson;

@Path("/session")
public class UserSessionService {

	/*@POST
	@Path("/login")
	public Response checkLogin(@FormParam("username") String userName,
			@FormParam("password") String userPassword) {
		// public Response checkLogin(JSONObject inputJsonObj) {
		/*
		 * System.out.println(inputJsonObj.toString()); JSONObject loginOutObj =
		 * new JSONObject(); String userName =
		 * inputJsonObj.getString("login-username"); String userPassword =
		 * inputJsonObj.getString("login-password");
		 *//*
		JSONObject loginOutObj = new JSONObject();
		try {

			LoginDao loginDaoObj = new LoginDao();
			if (loginDaoObj.authenticateUser(userName, userPassword)) {
				loginOutObj.put("loginCode", "S");
				loginOutObj.put("message", "Login Successful!");
			} else {
				loginOutObj.put("loginCode", "F");
				loginOutObj.put("message", "Login Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(loginOutObj.toString()).build();
	}*/

	@POST
	@Path("/logincheck")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkLoginNew(UserLogin usr) {
		// public Response checkLogin(JSONObject inputJsonObj) {
		/*
		 * System.out.println(inputJsonObj.toString()); JSONObject loginOutObj =
		 * new JSONObject(); String userName =
		 * inputJsonObj.getString("login-username"); String userPassword =
		 * inputJsonObj.getString("login-password");
		 */
		JSONObject loginOutObj = new JSONObject();
		try {
			System.out.println(usr.getUserName() + ":" + usr.getPassword());
			LoginDao loginDaoObj = new LoginDao();
			List<String> loginDetailList = loginDaoObj.authenticateUser(usr.getUserName(), usr.getPassword());
			String loginCode = loginDetailList.get(0);
			if (loginCode.equalsIgnoreCase("S")) {
				loginOutObj.put("loginCode", loginDetailList.get(0));
				loginOutObj.put("message", "Login Successful!");
				loginOutObj.put("role", loginDetailList.get(1));
			} else {
				loginOutObj.put("loginCode", "F");
				loginOutObj.put("message", "Login Failed!");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return Response.status(200).entity(loginOutObj.toString()).build();
	}

	@POST
	@Path("/sessionregister")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerEvaluationSession(
			EvalSessionRegDetails evalSessionObj) {

		JSONObject regOutObj = new JSONObject();
		try {
			String companyName = evalSessionObj.getCompanyName().trim();
			String selectedProducts[] = evalSessionObj.getSelectedProducts();
			/*int highVal = evalSessionObj.getHighVal();
			int mediumVal = evalSessionObj.getMediumVal(); 
			int lowVal = evalSessionObj.getLowVal();*/
			
			boolean saveStatus = new EvaluationRegisterationDao().saveEvalRegisterationDetails(companyName, selectedProducts);

			if (saveStatus) {
				regOutObj.put("regSaveCode", "S");
				regOutObj.put("message", "Registeration Successful!");
			} else {
				regOutObj.put("regSaveCode", "F");
				regOutObj.put("message", "Registeration Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(regOutObj.toString()).build();
	}

	@GET
	@Path("/checkCompany/{companyName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkCompName(@PathParam("companyName") String companyName) {
		JSONObject compStatusOutObj = new JSONObject();
		try {
			boolean compExist = new EvaluationRegisterationDao()
					.checkEvalCompName(companyName);
			if (compExist) {
				compStatusOutObj.put("compExistCode", "1");
				compStatusOutObj.put("message", "Company name already used!");
			} else {
				compStatusOutObj.put("compExistCode", "0");
				compStatusOutObj.put("message", "Correct!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(compStatusOutObj.toString()).build();
	}

	@POST
	@Path("/saveEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveEvaluationDetails(SaveEvaluationBean evalSessionObj) {

		JSONObject regOutObj = new JSONObject();
		try {
			boolean saveStatus = new EvaluationDao().saveEvaluationDetails(
					evalSessionObj.getCompName().trim(),
					evalSessionObj.toHashMap(evalSessionObj.getFeature_list()));

			if (saveStatus) {
				regOutObj.put("regSaveCode", "S");
				regOutObj.put("message", "saveEvaluationDetails Successful!");
			} else {
				regOutObj.put("regSaveCode", "F");
				regOutObj.put("message", "saveEvaluationDetails Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(regOutObj.toString()).build();
	}

	/*@POST
	@Path("/catdashboard")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//public Response getDashboard(@PathParam("compName") String compName, @PathParam("categoryId") String categoryId) {
	public Response getCategoryDashboard(DashboardInputBean dashboardInp) {
		String dataJson = null;
		try {
			Map<String, ArrayList<String>> scoreMap = null;
			if (dashboardInp.getCatId() != null && dashboardInp.getCompName() != null) {
				scoreMap = new EvaluationDao().getEvaluationCatScore(dashboardInp.getCompName(), dashboardInp.getCatId());
			}
			
			dataJson = new Gson().toJson(scoreMap);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Response.status(200).entity(dataJson).build();
	}*/

	/*
	@POST
	@Path("/dashboard")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDashboard(DashboardInputBean dashboardInp) {
		String dataJson = null;
		try {
			Map<String, ArrayList<String>> scoreMap = null;
			if (dashboardInp.getCompName() != null) {
				scoreMap = new EvaluationDao().getEvaluationScore(dashboardInp.getCompName());
			}
			//dataJson = scoreMap.toString();
			dataJson = new Gson().toJson(scoreMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(dataJson).build();
	}*/
	
	

	@POST
	@Path("/saveSignUpDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSignUpDetails(SignUpDetailsBean signUpDetails) {
		String dataJson = null;
		JSONObject regOutObj = new JSONObject();
		try {
			LoginDao signUp = new LoginDao();
			dataJson  = signUp.saveSignUpDetails(signUpDetails);
			
			if (dataJson.equalsIgnoreCase("S")) {
				regOutObj.put("signupCode", "S");
				regOutObj.put("message", "Signup Successful!");
			} else {
				regOutObj.put("signupCode", "F");
				regOutObj.put("message", "Signup Failed!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(regOutObj.toString()).build();
	}
}