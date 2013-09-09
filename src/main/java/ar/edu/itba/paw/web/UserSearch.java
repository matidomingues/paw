package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.UserHelper;

public class UserSearch extends HttpServlet{

	UserHelper usermanager = new UserHelper();
	
	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("users", usermanager.getAll());
		req.setAttribute("user", usermanager.getUserBySession(getSessionFromCookie(req.getCookies())));
		req.getRequestDispatcher("/WEB-INF/jsp/finduser.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", usermanager.find(req.getParameter("username")));
		req.setAttribute("user", usermanager.getUserBySession(getSessionFromCookie(req.getCookies())));
		req.getRequestDispatcher("/WEB-INF/jsp/finduser.jsp").forward(req, resp);
	}
}
