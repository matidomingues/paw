package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.model.User;

public class TwattAdd extends HttpServlet {

	private TwattHelper twattmanager = new TwattHelper();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean result = twattmanager.addTwatt((User) req.getSession()
				.getAttribute("user"), req.getParameter("message"));
		if (!result) {
			req.setAttribute("error", "No es posible Twattear en este momento");
		} else {
			req.setAttribute("success", "Se Twatteo correctamente");
		}
		req.getRequestDispatcher("/home").forward(req, resp);
	}
}
