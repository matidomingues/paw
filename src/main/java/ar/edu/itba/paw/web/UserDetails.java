package ar.edu.itba.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.DataManager;
import ar.edu.itba.paw.objects.User;

public class UserDetails extends HttpServlet {

	DataManager manager = DataManager.getInstance();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String[] url = req.getRequestURI().split("/");
		User user = manager.getUserByUsername(url[2]);
		if(user != null){
			req.setAttribute("user", user);
		}else{
			req.setAttribute("error", "No existe ningun usuario con ese nombre");
		}
		req.getRequestDispatcher("/WEB-INF/jsp/userdetail.jsp").forward(req, resp);
	}
}
