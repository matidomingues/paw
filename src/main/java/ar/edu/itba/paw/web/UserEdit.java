package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.DataManager;
import ar.edu.itba.paw.objects.User;

public class UserEdit extends HttpServlet{
	
	DataManager manager = DataManager.getInstance();
	
	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("uuid") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		User user = manager.getUserBySession(getSessionFromCookie(req.getCookies()));
		if(user != null){
			req.setAttribute("user", user);
		}else{
			req.setAttribute("error", "Unable to fetch user data");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
	
}
