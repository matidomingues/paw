package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.twatt.Twatt;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MessageConverterTag extends TagSupport {

    private Twatt twatt;

    @Override
    public int doStartTag() throws JspException {
        JspWriter output = pageContext.getOut();


        return EVAL_PAGE;
    }

    public Twatt getTwatt() {
        return  this.twatt;
    }

    public Twatt setTwatt(Twatt twatt) {
        return this.twatt = twatt;
    }
}
