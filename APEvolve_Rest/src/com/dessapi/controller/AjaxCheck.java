package com.dessapi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dessapi.dao.EvaluationRegisterationDao;

/**
 * Servlet implementation class AjaxCheck
 */
@WebServlet("/AjaxCheck")
public class AjaxCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxCheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || (session.getAttribute("loginStatus") == null || (!session.getAttribute("loginStatus").equals("success")))) {
			ServletContext context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/index");
			rd.forward(request, response);
		} else 
		{
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0);

			try {
				String comp = request.getParameter("compname").trim();
				boolean compExist = new EvaluationRegisterationDao().checkEvalCompName(comp);
				response.setContentType("text/html");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter out = response.getWriter();
				if(compExist) {
					out.print("Y<i class='fa fa-times fa-lg no'>Company name already used. Please use different name.</i>");					
				} else {
					out.print("N<i class='fa fa-check fa-lg yes'>Correct</i>");
				}				
			} catch (IOException io) {
				io.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
