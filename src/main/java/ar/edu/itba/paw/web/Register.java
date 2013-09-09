package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.manager.UserDAO;

public class Register extends HttpServlet {

	UserHelper usermanager = new UserHelper();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
				.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String extrapassword = req.getParameter("extrapassword");
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String description = req.getParameter("description");
		String secretQuestion = req.getParameter("secretquestion");
		String secretAnswer = req.getParameter("secretanswer");
		UUID uuid = usermanager.registerUser(username, password, name, surname,
				description, secretQuestion, secretAnswer);
		if (uuid != null) {
			resp.addCookie(new Cookie("TwitterUUID", uuid.toString()));
			resp.sendRedirect("home");
		} else {
			req.setAttribute("error", "Datos incorrectos");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
				.forward(req, resp);
	}
}
