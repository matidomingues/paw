package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.model.database.HashtagDAO;
import ar.edu.itba.paw.model.database.implamentations.HashtagDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


public class Home extends HttpServlet{
	
	UserHelper usermanager = new UserHelper();
	HashtagDAO hashtagmanager = HashtagDAOImpl.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("hashtags", hashtagmanager.getTrendingHashtags());
		req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
