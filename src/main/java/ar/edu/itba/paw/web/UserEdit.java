//package ar.edu.itba.paw.web;
//
//import ar.edu.itba.paw.model.User;
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//
//import org.apache.commons.fileupload.FileItem;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@SuppressWarnings("serial")
//public class UserEdit extends HttpServlet{
//	
//	UserService usermanager = UserServiceImpl.getInstance();
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        int user_id = (Integer)req.getSession().getAttribute("user_id");
//        User user = this.usermanager.find(user_id);
//        req.setAttribute("user_name", user.getName());
//        req.setAttribute("user_surname", user.getSurname());
//        req.setAttribute("user_description", user.getDescription());
//        req.setAttribute("user_password", user.getPassword());
//		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
//	}
//	
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		int userId = (Integer)req.getSession().getAttribute("user_id");
//        User user = this.usermanager.find(userId);
//		String password = req.getParameter("password");
//		String password2 = req.getParameter("password2");
//		
//		if(password.compareTo(password2) != 0){
//			req.setAttribute("error", "Las contraseñas no coinciden");
//			req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
//		}else{
//			user.setPassword(password);
//			user.setDescription(req.getParameter("description"));
//			user.setName(req.getParameter("name"));
//			user.setSurname(req.getParameter("surname"));
//            FileItem fileItem = (FileItem)req.getAttribute("photo");
//            if (fileItem != null) {
//                user.setPhoto(fileItem.get());
//            }
//			boolean result = usermanager.updateUser(user);
//			if(result){
//				req.setAttribute("success", "Actualizado con exito!");
//				req.getRequestDispatcher("/user/"+user.getUsername()).forward(req, resp);
//			}else{
//				req.setAttribute("error", "Error al actualizar");
//				req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
//			}
//		}		
//	}
//	
//}
