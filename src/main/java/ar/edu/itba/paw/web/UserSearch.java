package ar.edu.itba.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.manager.UserDAO;

public class UserSearch extends HttpServlet{

	UserHelper usermanager = new UserHelper();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("users", usermanager.getAll());
		req.getRequestDispatcher("/WEB-INF/jsp/finduser.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", usermanager.find(req.getParameter("username")));
		req.getRequestDispatcher("/WEB-INF/jsp/finduser.jsp").forward(req, resp);
	}
}
