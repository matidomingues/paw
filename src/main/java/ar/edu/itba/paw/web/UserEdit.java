package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.model.User;

public class UserEdit extends HttpServlet{
	
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
		User user = usermanager.getUserBySession(getSessionFromCookie(req.getCookies()));
		if(user != null){
			req.setAttribute("user", user);
		}else{
			req.setAttribute("error", "Unable to fetch user data");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UUID uuid = getSessionFromCookie(req.getCookies());
		String description = req.getParameter("description");
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String password = req.getParameter("password");
		
		boolean result = usermanager.updateUserByUUID(uuid, name, surname, password, description);
		
		if(result){
			req.getRequestDispatcher("home").forward(req, resp);
		}else{
			req.setAttribute("error", "Error al actualizar");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
}
