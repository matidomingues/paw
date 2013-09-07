package ar.edu.itba.paw.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.paw.manager.UserDAO;

public class AuthenticationFilter implements Filter {

	UserDAO usermanager = UserDAO.getInstance();
	
	private UUID getSessionFromCookie(Cookie[] cookies) {
        if (cookies == null) { return null; }

		for (Cookie cookie : cookies) {
			if (cookie.getName().compareTo("TwitterUUID") == 0) {
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		UUID uuid = getSessionFromCookie(req.getCookies());
		if (usermanager.getUserBySession(uuid) == null
				&& !req.getRequestURL().toString().contains("css/main.css")
				&& !req.getRequestURL().toString().contains("login")
				&& !req.getRequestURL().toString().contains("register")) {
			resp.sendRedirect("/login");
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
