package com.dessapi.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dessapi.dao.EvaluationDao;
import com.dessapi.dao.EvaluationRegisterationDao;
import com.opencsv.CSVWriter;

/**
 * Servlet implementation class ReportController
 */
@WebServlet(name="/ReportController",
urlPatterns="/reports"
		)
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		if(session == null ||(session.getAttribute("loginStatus")==null || (!session.getAttribute("loginStatus").equals("success"))))
		{			
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/index");
			rd.forward(request, response);
		}
		else {
			CSVWriter writer =null;				
			String compName = (String) session.getAttribute("compName");		
			List<String[]> tableExport = new EvaluationDao().getEvaluationCSVData(compName);
			 
			try {
				ServletOutputStream out=response.getOutputStream();  
				response.setContentType("text/csv");
			    response.setHeader("Content-Disposition","filename=evaluation_report.csv");
			    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out));
			    writer = new CSVWriter(buff);
			    //writer.write
			    writer.writeAll(tableExport);
			    
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
		}
		
	}

}
