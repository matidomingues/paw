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
//@SuppressWarnings("serial")
//public class Logout extends HttpServlet{
//
//	UserService usermanager = UserServiceImpl.getInstance();
//	
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//		req.getSession().invalidate();
//		resp.sendRedirect(req.getContextPath() + "/login");
//	}
//}
