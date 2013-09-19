package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRestore extends HttpServlet {

	UserHelper usermanager = UserHelperImpl.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/userrestore.jsp").forward(
				req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		resp.sendRedirect("/restore/user/"+username);
	}
}
