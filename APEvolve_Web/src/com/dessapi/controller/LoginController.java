package com.dessapi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dessapi.dao.LoginDao;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(name ="/LoginController",
urlPatterns="/loginCheck"
		)
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		
		
		String userName = request.getParameter("login-username").trim();
		String userPassword = request.getParameter("login-password").trim();
		
		LoginDao loginDaoObj = new  LoginDao();
		if(loginDaoObj.authenticateUser(userName, userPassword)) {
			HttpSession session = request.getSession();
			session.setAttribute("loginStatus", "success");
			session.setAttribute("loginUserName", userName);
			//response.sendRedirect("EvalSessionRegServlet");
			response.sendRedirect("registration");
			
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginStatus", "failed");
			session.setAttribute("loginMsg", "Username or password is incorrect!!");			
			//request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			response.sendRedirect("index");
		}
	}

}
