package com.dessapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.dessapi.bean.Features;
import com.dessapi.bean.ProductsBean;
import com.dessapi.dao.EvaluationDao;
import com.dessapi.dao.EvaluationRegisterationDao;

/**
 * Servlet implementation class EvalSessionRegServlet
 */
@WebServlet(
		name ="/EvalSessionRegServlet",
		urlPatterns = "/registration"
		)
public class EvalSessionRegController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvalSessionRegController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		
		HttpSession session = request.getSession(false);		
		if(session == null ||(session.getAttribute("loginStatus")==null || (!session.getAttribute("loginStatus").equals("success"))))
		{			
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/index");
			rd.forward(request, response);
		}
		else {			
			List<ProductsBean> productsList = new EvaluationRegisterationDao().getProductsList();		
			request.setAttribute("productMasterList", productsList);
			
			ServletContext context= getServletContext();
			//RequestDispatcher rd= context.getRequestDispatcher("/jsp/evalSessionRegister.jsp");
			RequestDispatcher rd= context.getRequestDispatcher("/ap-content/evalSessionRegister.jsp");
			rd.forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		if(session == null ||(session.getAttribute("loginStatus")==null || (!session.getAttribute("loginStatus").equals("success"))))
		{			
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/index");
			rd.forward(request, response);
		}
		else {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0);
			
			String companyName = request.getParameter("companyName").trim();
			String selectedProducts[] =  request.getParameterValues("selectedproducts");
			int highVal = Integer.parseInt(request.getParameter("highVal"));
			int mediumVal = Integer.parseInt(request.getParameter("mediumVal"));
			int lowVal = Integer.parseInt(request.getParameter("lowVal"));
			HashMap<String, Integer> priorityValMap = null;		
			Map categoryMastMap = null; 
							
			try {
				//
			
				new EvaluationRegisterationDao().saveEvalRegisterationDetails(companyName, selectedProducts, lowVal, mediumVal, highVal);
				session.setAttribute("compName", companyName);
				priorityValMap = new HashMap<String, Integer>(); 
				priorityValMap.put("high", Integer.valueOf(highVal));
				priorityValMap.put("medium", Integer.valueOf(mediumVal));
				priorityValMap.put("low", Integer.valueOf(lowVal));
				request.setAttribute("weightageVals", priorityValMap);
				session.setAttribute("weightages", priorityValMap);
				
				categoryMastMap = new EvaluationRegisterationDao().getCategoryList();
				session.setAttribute("catMast", categoryMastMap);
				List<ProductsBean> selectedProdList = new EvaluationDao().getSelectedProductsList(selectedProducts);
				session.setAttribute("selectedProd", selectedProdList);
				List<Features> featureList = new EvaluationDao().getFeatuesList();
				request.setAttribute("featuresListObj", featureList );
				ServletContext context= getServletContext();
				//RequestDispatcher rd= context.getRequestDispatcher("/jsp/evalFeatures.jsp");
				RequestDispatcher rd= context.getRequestDispatcher("/ap-content/evalFeatures.jsp");
				rd.forward(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

