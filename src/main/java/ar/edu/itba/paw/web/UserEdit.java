package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.UserDAO;
import ar.edu.itba.paw.objects.User;

public class UserEdit extends HttpServlet{
	
	UserDAO usermanager = UserDAO.getInstance();
	
	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		User user = usermanager.getUserBySession(getSessionFromCookie(req.getCookies()));
		if(user != null){
			req.setAttribute("user", user);
		}else{
			req.setAttribute("error", "Unable to fetch user data");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User user = usermanager.getUserBySession(getSessionFromCookie(req.getCookies()));
		if(user != null){
			user.setDescription(req.getParameter("description"));
			user.setName(req.getParameter("name"));
			user.setPassword(req.getParameter("password"));
			user.setDescription(req.getParameter("description"));
			req.getRequestDispatcher("home").forward(req, resp);
		}else{
			req.setAttribute("error", "Unable to fetch user data");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
}
