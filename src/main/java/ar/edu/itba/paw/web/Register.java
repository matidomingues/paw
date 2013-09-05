package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.DataManager;

public class Register extends HttpServlet {
	
	DataManager manager = DataManager.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String extrapassword = req.getParameter("extrapassword");
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String description = req.getParameter("description");
		if(manager.getUserByUsername(username) != null){
			req.setAttribute("error", "Ya existe un usuario con ese nombre de usuario ");
			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
		}else if(password.compareTo(extrapassword) != 0){
			req.setAttribute("error", "Las contraseñas no coinciden");
			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
		}else if(!manager.registerUser(username, password, name, surname, description)){
			req.setAttribute("error", "El sistema no puede procesar su pedido actualmente");
			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
		}else{
			
			//Mandar directo al post de login
			UUID user = manager.authenticate(username, password);
			resp.addCookie(new Cookie("TwitterUUID", user.toString()));
			resp.sendRedirect("home");
			
		}
	}
}
