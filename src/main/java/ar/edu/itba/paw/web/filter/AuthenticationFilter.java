//package ar.edu.itba.paw.web.filter;
//
//import ar.edu.itba.paw.service.UserService;
//import ar.edu.itba.paw.service.implementations.UserServiceImpl;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.util.UUID;
//
//public class AuthenticationFilter implements Filter {
//
//	UserService usermanager;
//	
//	@Autowired
//	public AuthenticationFilter(UserService userService){
//		this.usermanager = userService;
//	}
//
//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse resp = (HttpServletResponse) response;
//
//		boolean logued = req.getSession().getAttribute("user_id") != null;
//		boolean css = req.getRequestURL().toString().contains("css/main.css");
//		boolean login = req.getRequestURL().toString().contains("login");
//		boolean register = req.getRequestURL().toString().contains("register");
//		boolean restore = req.getRequestURL().toString().contains("restore");
//
//		if (!logued && !css && !login && !register && !restore) {
//			resp.sendRedirect(req.getContextPath() +"/login");
//		} else if (logued && !css && (login || register || restore)) {
//			resp.sendRedirect(req.getContextPath() +"/home");
//		} else {
//			chain.doFilter(request, response);
//		}
//	}
//
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void init(FilterConfig arg0) throws ServletException {
//		// TODO Auto-generated method stub
//
//	}
//}
