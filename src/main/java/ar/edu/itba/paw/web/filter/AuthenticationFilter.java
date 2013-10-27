package ar.edu.itba.paw.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		boolean logued = req.getSession().getAttribute("user_id") != null;
		boolean login = req.getRequestURL().toString().contains("login");
		boolean profile = req.getRequestURL().toString().contains("profile");
		boolean find = req.getRequestURL().toString().contains("find");
		boolean register = req.getRequestURL().toString().contains("register");
		boolean restore = req.getRequestURL().toString().contains("restore");

		if (!logued && !login && !register && !restore && !profile && !find) {
			resp.sendRedirect(req.getContextPath() +"/bin/user/login");
		} else if (logued && (login || register || restore)) {
			resp.sendRedirect(req.getContextPath() +"/bin/home");
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
