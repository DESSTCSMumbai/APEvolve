package com.dessapi.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.dessapi.bean.EvalScoreDetails;
import com.dessapi.dao.EvaluationDao;
import com.dessapi.dao.EvaluationRegisterationDao;

/**
 * Servlet implementation class evaluationController
 */
@WebServlet(
		name="/EvaluationController",
		urlPatterns="/evaluation"
		)
public class EvaluationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null ||(session.getAttribute("loginStatus")==null || (!session.getAttribute("loginStatus").equals("success"))))
		{			
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/index");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session == null ||(session.getAttribute("loginStatus")==null || (!session.getAttribute("loginStatus").equals("success"))))
		{			
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/index");
			rd.forward(request, response);
		}
		else {
			String selectedFeatures[] = request.getParameterValues("evalFeatures");
			Map<String, Integer> featureEvalMap = new HashMap<String, Integer>();
			int featureRating = 0;
			try {
				if((selectedFeatures!=null) && selectedFeatures.length!=0){
					for (int featuresIdx = 0; featuresIdx < selectedFeatures.length; featuresIdx++) {
						
						featureRating = ((request.getParameter(selectedFeatures[featuresIdx])!=null &&  !(request.getParameter(selectedFeatures[featuresIdx]).equals(""))) ? Integer.parseInt(request.getParameter(selectedFeatures[featuresIdx])) :0 ); //;
						featureEvalMap.put(selectedFeatures[featuresIdx], Integer.valueOf(featureRating));
						//System.out.println(selectedFeatures[featuresIdx] + ":" + featureRating );
					}
					String compName = (String) session.getAttribute("compName");
					new EvaluationDao().saveEvaluationDetails(compName, featureEvalMap);
					//Map<String, ArrayList<String>> scoreMap = new EvaluationDao().getEvaluationScore(compName);
					
					//request.setAttribute("scoreMapObj", scoreMap);
					//ServletContext context= getServletContext();	
					//RequestDispatcher rd= context.getRequestDispatcher("/jsp/evalCategorywiseDashboard.jsp");
					//rd.forward(request, response);
					//response.sendRedirect("jsp/evalCategorywiseDashboard.jsp");
					
					//response.sendRedirect("/APEvolve/DashboardController");
					response.sendRedirect("/APEvolve/dashboard");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}				
	}

}
