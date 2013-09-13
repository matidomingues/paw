package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

public class UserDetails extends HttpServlet {

	private UserHelper usermanager = new UserHelper();
	private TwattHelper twatmanager = new TwattHelper();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String[] url = req.getRequestURI().split("/");
		User user = usermanager.getUserByUsername(url[2]);
		if(user != null){
			req.setAttribute("user", user);
			List<Twatt> twatts = twatmanager.getTwattsByUsername(url[2]);
			for(Twatt twatt: twatts){
				twatt.setMessage(MessageHelper.prepareMessage(twatt.getMessage()));
			}
			req.setAttribute("twatts", twatts);
		}else{
			req.setAttribute("error", "No existe ningun usuario con ese nombre");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/userdetail.jsp").forward(req, resp);
	}
}
