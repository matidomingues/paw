package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.model.database.HashtagDAO;
import ar.edu.itba.paw.model.database.UserDAO;
import ar.edu.itba.paw.model.database.implamentations.HashtagDAOImpl;
import ar.edu.itba.paw.model.database.implamentations.UserDAOImpl;

public class Home extends HttpServlet{
	
	UserDAO usermanager = UserDAOImpl.getInstance();
	HashtagDAO hashtagmanager = HashtagDAOImpl.getInstance();
	
	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("hashtags", hashtagmanager.getTrendingHashtags());
		req.setAttribute("user", usermanager.getUserBySession(getSessionFromCookie(req.getCookies())));
		req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
	}

}
