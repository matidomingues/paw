package ar.edu.itba.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.UrlDAO;

public class URLShortener extends HttpServlet{

	UrlDAO urlmanager = UrlDAO.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String[] url = req.getRequestURI().split("/");
		String redirect = urlmanager.resolve(url[2]);
		resp.sendRedirect(redirect);
	}
}
