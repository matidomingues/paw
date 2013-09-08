package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.manager.TwattDAO;
import ar.edu.itba.paw.manager.UserDAO;
import ar.edu.itba.paw.model.User;

public class TwattAdd extends HttpServlet{

	private TwattHelper twattmanager = new TwattHelper();
	
	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean result = twattmanager.addTwatt(getSessionFromCookie(req.getCookies()), req.getParameter("message"));
		if(!result){
			req.setAttribute("error", "No es posible Twattear en este momento");
		}
		resp.sendRedirect("home");
	}
}
