package ar.edu.itba.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.model.database.UserDAO;
import ar.edu.itba.paw.model.database.implamentations.UserDAOImpl;

public class UserRestore extends HttpServlet{

	UserDAO usermanager = UserDAOImpl.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
	}
}
