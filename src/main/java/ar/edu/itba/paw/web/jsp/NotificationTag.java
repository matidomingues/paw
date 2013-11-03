package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.notification.FollowingNotification;
import ar.edu.itba.paw.domain.notification.MentionNotification;
import ar.edu.itba.paw.domain.notification.Notification;
import ar.edu.itba.paw.domain.notification.RetwattNotification;
import ar.edu.itba.paw.domain.twatt.Retwatt;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.helper.MessageHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class NotificationTag extends TagSupport {

    private Notification notification;

    private Twatt twatt;

    private ApplicationContext applicationContext;

    @Autowired
    private MessageHelper messageHelper;

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public int doStartTag() throws JspException {
        try {
            init();
            sessionFactory.getCurrentSession().beginTransaction();
            JspWriter output = pageContext.getOut();
            String result = print(notification);
            if (result != null) {
                output.write(print(notification));
            }
            sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
        return EVAL_PAGE;
    }

    private void init() {
        this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setNotification(Object notification) {
        try {
            this.setNotification((Notification) notification);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Not a notification",cce);
        }
    }

    public Twatt getTwatt() {
        return this.twatt;
    }

    public void setTwatt(Twatt twatt) {
        this.twatt = twatt;
    }

    public void setTwatt(Object twatt) {
        try {
            this.setTwatt((Twatt)twatt);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Not a twatt", cce);
        }
    }

    private String print(Notification notification) {
        String result = null;
        if (notification instanceof RetwattNotification) {
            result = printTwatt(((RetwattNotification)notification).getRetwatt());
        } else if (notification instanceof MentionNotification) {
            result = printTwatt(((MentionNotification) notification).getTwatt());
        } else if (notification instanceof FollowingNotification) {
            FollowingNotification fn = (FollowingNotification)notification;
            result = new StringBuilder()
                        .append("<tr><td><div><img src=\"/bin/image/")
                        .append(fn.getFollower().getUsername())
                        .append("\"></div></td><td colspan=\"3\">El usuario <a href=\"/bin/user/profile/")
                        .append(fn.getFollower().getUsername())
                        .append("\">")
                        .append(fn.getFollower().getUsername())
                        .append("</a> te ha empezado a seguir!</td></tr>")
                        .toString();
        }
        return result;
    }

    private String printTwatt(Twatt twatt) {
        return "        <tr><td>\n" +
                "            <div>\n" +
                "            <img src=\"" + ((HttpServletRequest)pageContext.getRequest()).getContextPath() + "/bin/image/<c:out value='" + twatt.getCreator().getUsername() + "'/>\">\n" +
                "            </div>\n" +
                "        </td>\n" +
                "        <td>\n" +
                ((twatt instanceof Retwatt) ? ((Retwatt)twatt).getCreator().getUsername() + ":" + ((Retwatt)twatt).getOriginalTwatt().getCreator().getUsername() : twatt.getCreator().getUsername()) +
                "        </td>\n" +
                "        <td>\n" +
                this.messageHelper.prepareMessage(((HttpServletRequest)pageContext.getRequest()).getContextPath(), twatt.getMessage()) +
                "        </td>\n" +
                "        <td>\n" +
                twatt.getTimestamp() +
                "        </td>\n" +
                "        <td>\n" +
                printTwattActions(twatt) +
                "        </td></tr>";
    }

    private String printTwattActions(Twatt twatt) {
        return deleteAction(twatt) + retwattAction(twatt) + favouriteAction(twatt);
    }

    private String favouriteAction(Twatt twatt) {
        StringBuilder sb = new StringBuilder("");
        if (notification.getRecipient().isFavourite(twatt)) {
            sb.append("<form method=\"POST\" action=\"/bin/twatt/unfavourite\">\n<input type=\"hidden\" name=\"twattId\" value=\"");
            sb.append(twatt.getId());
            sb.append("\"/>\n<button title=\"Eliminar de Favoritos\" type=\"submit\" class=\"pull-right btn btn-primary\" value=\"Desfavoritear\"><span class=\"glyphicon glyphicon-heart\"></span></button>\n</form>");
        } else {
            sb.append("<form method=\"POST\" action=\"/bin/twatt/favourite\">\n<input type=\"hidden\" name=\"twattId\" value=\"");
            sb.append(twatt.getId());
            sb.append("\"/>\n<button title=\"Agregar a Favoritos\" type=\"submit\" class=\"pull-right btn btn-primary\" value=\"Favoritear\"><span class=\"glyphicon glyphicon-heart-empty\"></span></button>\n</form>");
        }
        return sb.toString();
    }

    private String retwattAction(Twatt twatt) {
        return "<form method=\"POST\" action=\"/bin/twatt/retwatt\">\n<input type=\"hidden\" name=\"twattId\" value=\"" +
                twatt.getId() + "\"/>\n<button title=\"Retwatt\" type=\"submit\" class=\"pull-right btn btn-primary\" value=\"Retwatt\"><span class=\"glyphicon glyphicon-share\"></span></button>\n</form>";    }

    private String deleteAction(Twatt twatt) {
        StringBuilder sb = new StringBuilder("");
        if (twatt.getCreator().equals(notification.getRecipient())) {
            sb.append("<form method=\"POST\" action=\"/bin/twatt/delete\">\n<input type=\"hidden\" name=\"twattId\" value=\"");
            sb.append(twatt.getId());
            sb.append("\"/>\n<button title=\"Eliminar\" type=\"submit\" class=\"pull-right btn btn-primary\" value=\"Eliminar\"><span class=\"glyphicon glyphicon-remove\"></span></button>\n</form>");
        }
        return sb.toString();    }
}
