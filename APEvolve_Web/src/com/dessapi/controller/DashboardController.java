package com.dessapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dessapi.dao.EvaluationDao;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet(
		name ="/DashboardController",
		urlPatterns = "/dashboard"
		)
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String srcParam = request.getParameter("src");
		String compName= null;
		HttpSession session = request.getSession(false);
		if(session == null ||(session.getAttribute("loginStatus")==null || (!session.getAttribute("loginStatus").equals("success"))))
		{			
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/index");
			rd.forward(request, response);
		}
		else {					
			compName = (String)session.getAttribute("compName");
			if( srcParam==null || srcParam.equals("") || srcParam.equals("null")) {
				 
				
				Map<String, ArrayList<String>> scoreMap = new EvaluationDao().getEvaluationScore(compName);		 
				//new EvaluationDao().getEvaluationScoreDetails(compName);
				request.setAttribute("scoreMapObj", scoreMap);
				ServletContext context= getServletContext();
				RequestDispatcher rd= context.getRequestDispatcher("/ap-content/evalCategorywiseDashboard.jsp");
				rd.forward(request, response);
			} else if (srcParam.equals("rpt")) {
				String cat = request.getParameter("category").trim();
				Map<String, ArrayList<String>> scoreMap = new EvaluationDao().getEvaluationCatScore(compName, cat);
				request.setAttribute("selectedCat", cat);
				/*for (Map.Entry<String, ArrayList<String>> entry : scoreMap.entrySet()) {
				    String key = entry.getKey();
				    ArrayList<String> value = entry.getValue();
				    for(String aString : value){
				        System.out.println("key : " + key + " value : " + aString);
				    }
				}*/
				
				request.setAttribute("scoreMapObj", scoreMap);
				ServletContext context= getServletContext();
				RequestDispatcher rd= context.getRequestDispatcher("/ap-content/evalFeaturewiseDashboard.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
