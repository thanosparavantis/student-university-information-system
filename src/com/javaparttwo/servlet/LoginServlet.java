package com.javaparttwo.servlet;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.javaparttwo.model.User;
import com.javaparttwo.service.AuthService;
import com.javaparttwo.service.LoginService;

/**
 * Servlet implementation class Login
 */
@WebServlet({ "/LoginServlet", "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/javapart2")
	private DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AuthService auth = new AuthService(request, response);
		HttpSession session = request.getSession();
		
		if (auth.isLoggedIn()) {
			User user = (User)session.getAttribute("user");
			response.sendRedirect(user.getRoleId());
		} else {
			request.getRequestDispatcher("index.html").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LoginService login = new LoginService(ds);
		HttpSession session = request.getSession();
		
		User user = login.auth(request.getParameter("username"), request.getParameter("password"));
		
		if (user != null) {
			session = request.getSession();
			session.setAttribute("loggedIn", true);
			session.setAttribute("user", user);
			
			response.sendRedirect(user.getRoleId());
		} else {
			response.sendRedirect("login");
		}
	}

}
