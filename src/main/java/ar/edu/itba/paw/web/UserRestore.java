//package ar.edu.itba.paw.web;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//
//@SuppressWarnings("serial")
//public class UserRestore extends HttpServlet {
//
//	UserService usermanager = UserServiceImpl.getInstance();
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		req.getRequestDispatcher("/WEB-INF/jsp/userrestore.jsp").forward(
//				req, resp);
//	}
//	
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String username = req.getParameter("username");
//		resp.sendRedirect(req.getContextPath() + "/restore/user/"+username);
//	}
//}
