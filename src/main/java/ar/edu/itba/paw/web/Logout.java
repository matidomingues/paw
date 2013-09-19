package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout extends HttpServlet{

	UserHelper usermanager = UserHelperImpl.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getSession().invalidate();
		resp.sendRedirect("login");
	}
}
