package com.dessapi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dessapi.dao.LoginDao;

/**
 * Servlet implementation class SignUpController
 */
@WebServlet(
		name="/SignUpController",
		urlPatterns="/signupcheck"
		)
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		
		try {
			String firstName = request.getParameter("firstName").trim();
			String lastName = request.getParameter("lastName").trim();
			String email = request.getParameter("email").trim();
			String phone = request.getParameter("phone").trim();
			String password = request.getParameter("password").trim();
			String company = request.getParameter("company").trim();
			String designation = request.getParameter("designation");
			
			LoginDao loginDaoObj = new  LoginDao();
			
			/*
			 * Not in use
			 * */
			//String signupStatus = loginDaoObj.saveSignUpDetails (firstName, lastName, email, password, phone, designation, company);
			String signupStatus =null;
			
			
			if(signupStatus.equalsIgnoreCase("S")) {
				request.setAttribute("signUpMsg", "Registration Successful! We will get back to you.");							
				//response.sendRedirect("signup");
			} else if(signupStatus.equalsIgnoreCase("E")) {
				request.setAttribute("signUpMsg", "This email id is already registered!");							
				//response.sendRedirect("signup");
			} else if(signupStatus.equalsIgnoreCase("F")) {
				request.setAttribute("signUpMsg", "Registration failed!");							
				//response.sendRedirect("signup");
			}
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/signup");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
