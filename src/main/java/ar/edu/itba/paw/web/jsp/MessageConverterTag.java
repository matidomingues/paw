package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.helper.MessageHelper;
import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@Component
public class MessageConverterTag extends TagSupport {

    private Twatt twatt;

    @Autowired
    private MessageHelper messageHelper;

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

    public Twatt setTwatt(Twatt twatt) {
        return this.twatt = twatt;
    }
}
