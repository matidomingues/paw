//package ar.edu.itba.paw.web.filter;
//
//import ar.edu.itba.paw.manager.ConnectionManager;
//import ar.edu.itba.paw.manager.DatabaseException;
//
//import javax.servlet.*;
//import java.io.IOException;
//
//public class ConnectionFilter implements Filter {
//    public void init(FilterConfig filterConfig) throws ServletException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            filterChain.doFilter(servletRequest, servletResponse);
//            ConnectionManager.getInstance().getConnection().commit();
//        } catch (Exception e) {
//            rollback(e);
//        } finally {
//            try {
//            ConnectionManager.getInstance().getConnection().close();
//            } catch (Exception e) {
//                throw new DatabaseException("Problems while closing the connection", e);
//            }
//        }
//    }
//
//    private void rollback(Exception e) {
//        try {
//            ConnectionManager.getInstance().getConnection().rollback();
//        } catch (Exception ex) {
//            throw new DatabaseException("Problems while rollbacking", ex);
//        }
//        throw new RuntimeException(e);
//    }
//    public void destroy() {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//}
