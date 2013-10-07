package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UserQuestion extends HttpServlet {

	UserHelper usermanager = UserHelperImpl.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] url = req.getRequestURI().split("/");
		User user = usermanager.getUserByUsername(url[url.length-1]);
		if (user != null) {
			req.setAttribute("restoreuser", user);
			req.getRequestDispatcher("/WEB-INF/jsp/userrestorequestion.jsp")
					.forward(req, resp);
		} else {
			req.setAttribute("error", "Usuario inexistente");
			req.getRequestDispatcher("/WEB-INF/jsp/userrestore.jsp").forward(
					req, resp);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] url = req.getRequestURI().split("/");
		String secretAnswer = req.getParameter("secretanswer");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmpassword");
		if(password.compareTo(confirmPassword) != 0){
			req.setAttribute("error", "Las Contraseñas no Coinciden");
			doGet(req, resp);
		}else if(usermanager.userRestore(url[url.length-1], secretAnswer, password)){
			resp.sendRedirect(req.getContextPath() + "/login");
		}else{
			req.setAttribute("error", "Respuesta Incorrecta");
			doGet(req, resp);
		}
	}
}
