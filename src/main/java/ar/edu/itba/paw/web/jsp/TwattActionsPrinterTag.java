package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TwattActionsPrinterTag extends TagSupport {

    private Twatt twatt;
    private TwattUser user;

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter output = pageContext.getOut();
            if (user != null) {
                output.write(deleteAction(this.twatt, this.user));
                output.write(retweetAction(this.twatt, this.user));
                output.write(favouriteAction(this.twatt, this.user));
            }
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }

        return EVAL_PAGE;
    }

    private String deleteAction(Twatt twatt, TwattUser user) {
        StringBuilder sb = new StringBuilder("");
        if (twatt.getCreator().equals(user)) {
            sb.append("<form method=\"POST\" action=\"/bin/twatt/delete\">\n<input type=\"hidden\" name=\"twattId\" value=\"");
            sb.append(twatt.getId());
            sb.append("\"/>\n<button title=\"Eliminar\" type=\"submit\" class=\"pull-right btn btn-primary\" value=\"Eliminar\"><span class=\"glyphicon glyphicon-remove\"></span></button>\n</form>");
        }
        return sb.toString();
    }

    private String retweetAction(Twatt twatt, TwattUser user) {
        return "<form method=\"POST\" action=\"/bin/twatt/retwatt\">\n<input type=\"hidden\" name=\"twattId\" value=\"" +
                twatt.getId() + "\"/>\n<button title=\"Retwatt\" type=\"submit\" class=\"pull-right btn btn-primary\" value=\"Retwatt\"><span class=\"glyphicon glyphicon-share\"></span></button>\n</form>";
    }

    private String favouriteAction(Twatt twatt, TwattUser user) {
        StringBuilder sb = new StringBuilder("");
        if (user.isFavourite(twatt)) {
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

    public void setTwatt(Object twatt) {
        try {
        this.setTwatt((Twatt)twatt);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Not a Twatt", cce);
        }
    }

    public void setTwatt(Twatt twatt) {
        this.twatt = twatt;
    }

    public void setUser(Object twattUser) {
        try {
            this.setUser((TwattUser) twattUser);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Not a twattUser", cce);
        }
    }

    public void setUser(TwattUser twattUser) {
        this.user = twattUser;
    }

    public Twatt getTwatt() {
        return  this.twatt;
    }

    public TwattUser getUser() {
        return  this.user;
    }
}
