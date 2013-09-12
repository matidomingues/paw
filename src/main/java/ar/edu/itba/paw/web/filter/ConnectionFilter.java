package ar.edu.itba.paw.web.filter;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;

import javax.servlet.*;
import java.awt.color.CMMException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            ConnectionManager.getInstance().setConnection();
            filterChain.doFilter(servletRequest, servletResponse);
            ConnectionManager.getInstance().getConnection().commit();
        } catch (Exception e) {
            rollback(e);
        }
    }

    private void rollback(Exception e) {
        try {
            ConnectionManager.getInstance().getConnection().rollback();
        } catch (Exception ex) {
            throw new DatabaseException("Problems while rollbacking", ex);
        }
        throw new RuntimeException(e);
    }
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
