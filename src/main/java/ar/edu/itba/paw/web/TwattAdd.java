package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.TwattDAO;
import ar.edu.itba.paw.manager.UserDAO;
import ar.edu.itba.paw.model.User;

public class TwattAdd extends HttpServlet{

	private UserDAO usermanager = UserDAO.getInstance();
	private TwattDAO twatmanager = TwattDAO.getInstance();
	
	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = req.getParameter("message");
		User user = usermanager.getUserBySession(getSessionFromCookie(req.getCookies()));
		if(user != null){
			twatmanager.addTwatt(user, message);
		}
	}
}
