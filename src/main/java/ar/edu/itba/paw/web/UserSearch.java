//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@SuppressWarnings("serial")
//public class UserSearch extends HttpServlet{
//
//	UserService usermanager = UserServiceImpl.getInstance();
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//		req.setAttribute("users", usermanager.getAll());
//		req.getRequestDispatcher("/WEB-INF/jsp/finduser.jsp").forward(req, resp);
//	}
//	
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setAttribute("users", usermanager.find(req.getParameter("username")));
//		req.getRequestDispatcher("/WEB-INF/jsp/finduser.jsp").forward(req, resp);
//	}
//}
