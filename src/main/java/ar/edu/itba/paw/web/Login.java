package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.DataManager;

public class Login extends HttpServlet {

	DataManager manager = DataManager.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UUID user = manager.authenticate(username, password);
		if(user != null){
			resp.addCookie(new Cookie("TwitterUUID", user.toString()));
			resp.sendRedirect("home");
		}else{
			req.setAttribute("username", username);
			req.setAttribute("error", "Nombre de usuario o contraseña inválidos");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		}
	}
}