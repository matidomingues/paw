package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.twatt.Retwatt;
import ar.edu.itba.paw.domain.twatt.Twatt;
import org.springframework.util.Assert;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TwattAuthorPrinterTag extends TagSupport {

    private Twatt twatt;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter output = pageContext.getOut();
            if (twatt instanceof Retwatt) {
                Retwatt retwatt = (Retwatt) twatt;
                output.write(retwatt.getCreator().getUsername() + ":" + retwatt.getOriginalTwatt().getCreator().getUsername());
            } else {
                output.write(twatt.getCreator().getUsername());
            }
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
        return EVAL_PAGE;
    }

    public void setTwatt(Twatt twatt) {
        Assert.notNull(twatt);
        this.twatt = twatt;
    }

    public void setTwatt(Object twatt) {
        try {
            this.setTwatt((Twatt) twatt);
        } catch (ClassCastException cce) {
            throw  new IllegalArgumentException("Not a twatt");
        }
    }

    public Twatt getTwatt() {
        return this.twatt;
    }
}
