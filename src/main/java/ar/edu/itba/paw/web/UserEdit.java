package ar.edu.itba.paw.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.UserHelperImpl;
import ar.edu.itba.paw.model.User;

public class UserEdit extends HttpServlet{
	
	UserHelper usermanager = new UserHelperImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("user");
		String password = req.getParameter("password");
		String password2 = req.getParameter("password2");
		
		if(password.compareTo(password2) != 0){
			req.setAttribute("error", "Las contraseñas no coinciden");
		}else{
			user.setDescription(req.getParameter("description"));
			user.setName(req.getParameter("name"));
			user.setSurname(req.getParameter("surname"));
			boolean result = usermanager.updateUser(user);
			if(result){
				req.setAttribute("success", "Actualizado con exito!");
				req.getRequestDispatcher("/user/"+user.getUsername()).forward(req, resp);
			}else{
				req.setAttribute("error", "Error al actualizar");
			}
		}		
		req.getRequestDispatcher("/WEB-INF/jsp/useredit.jsp").forward(req, resp);
	}
	
}
