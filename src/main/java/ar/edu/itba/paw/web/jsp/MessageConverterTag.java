package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.helper.impl.MessageHelperImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MessageConverterTag extends TagSupport {

    private Twatt twatt;

    private MessageHelper messageHelper = new MessageHelperImpl();

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter output = pageContext.getOut();
            String preparedMessage = messageHelper.prepareMessage(((HttpServletRequest)pageContext.getRequest()).getContextPath(), twatt.getMessage());
            output.print(preparedMessage);
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }
        return EVAL_PAGE;
    }

    public Twatt getTwatt() {
        return  this.twatt;
    }

    public void setTwatt(Twatt twatt) {
        this.twatt = twatt;
    }

    public void setTwatt(Object twatt) {
        try{
            this.setTwatt((Twatt)twatt);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Not a twatt");
        }
    }
}
