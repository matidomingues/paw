package ar.edu.itba.paw.web.filter;

import ar.edu.itba.paw.utils.SiteMap;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		boolean logged = req.getSession().getAttribute("user_id") != null;

        if (logged) {
            if (SiteMap.needsAuthentication(req.getRequestURI())) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(req.getContextPath() + SiteMap.HOME.getAddress());
            }
        } else {
            if (SiteMap.needsAnonimity(req.getRequestURI())) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(req.getContextPath() + SiteMap.LOGIN.getAddress());
            }
        }

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
