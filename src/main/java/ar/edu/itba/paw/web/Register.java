//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.User;
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//
//import org.apache.commons.fileupload.FileItem;
//import org.joda.time.DateTime;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@SuppressWarnings("serial")
//public class Register extends HttpServlet {
//
//	UserService usermanager = UserServiceImpl.getInstance();
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		req.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
//				.forward(req, resp);
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		String username = req.getParameter("username");
//		String password = req.getParameter("password");
//		String extrapassword = req.getParameter("extrapassword");
//		String name = req.getParameter("name");
//		String surname = req.getParameter("surname");
//		String description = req.getParameter("description");
//		String secretQuestion = req.getParameter("secretquestion");
//		String secretAnswer = req.getParameter("secretanswer");
//        FileItem fileItem = (FileItem)req.getAttribute("photo");
//        byte[] photo = (fileItem == null)? new byte[0]:fileItem.get();
//		if(password.compareTo(extrapassword) != 0){
//			req.setAttribute("error", "Las contraseñas no coinciden");
//			req.setAttribute("username", username);
//			req.setAttribute("name", name);
//			req.setAttribute("surname", surname);
//			req.setAttribute("description", description);
//			req.setAttribute("secretQuestion", secretQuestion);
//			req.setAttribute("secretAnswer", secretAnswer);
//			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,
//                    resp);
//        }else if(usermanager.registerUser(new User(username, password, name, surname, description,
//				DateTime.now(), secretQuestion, secretAnswer, photo))){
//			req.getRequestDispatcher("login").forward(req, resp);
//		} else {
//			req.setAttribute("error", "El usuario ya existe");
//			req.setAttribute("username", username);
//			req.setAttribute("name", name);
//			req.setAttribute("surname", surname);
//			req.setAttribute("description", description);
//			req.setAttribute("secretQuestion", secretQuestion);
//			req.setAttribute("secretAnswer", secretAnswer);
//			req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,
//					resp);
//		}
//	}
//}
