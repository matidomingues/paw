package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.MessageHelperImpl;
import ar.edu.itba.paw.helper.implementations.TwattHelperImpl;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserDetails extends HttpServlet {

	private UserHelper usermanager = UserHelperImpl.getInstance();
	private TwattHelper twatmanager = TwattHelperImpl.getInstance();
    private MessageHelper messageHelper = MessageHelperImpl.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String[] url = req.getRequestURI().split("/");
		User user = usermanager.getUserByUsername(url[2]);
		if(user != null){
			req.setAttribute("searchuser", user);
			List<Twatt> twatts = twatmanager.getTwattsByUsername(url[2]);
			for(Twatt twatt: twatts){
				twatt.setMessage(messageHelper.prepareMessage(twatt.getMessage()));
			}
			req.setAttribute("twatts", twatts);
			req.getRequestDispatcher("/WEB-INF/jsp/userdetail.jsp").forward(req, resp);
		}else{
			req.setAttribute("error", "No existe ningun usuario con ese nombre");
			req.getRequestDispatcher("/find").forward(req, resp);
			
		}
		
		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doGet(req, resp);
	}
}
