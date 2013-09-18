package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.User;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {

	UserHelper usermanager = UserHelperImpl.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = usermanager.authenticate(username, password);
		if(user != null){
			req.getSession().setAttribute("user", user);
			req.setAttribute("success", "Bienvenido "+ username + " a Twatter!");
			req.getRequestDispatcher("home").forward(req, resp);
		}else{
			req.setAttribute("username", username);
			req.setAttribute("error", "Nombre de usuario o contraseña inválidos");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		}
	}
}
