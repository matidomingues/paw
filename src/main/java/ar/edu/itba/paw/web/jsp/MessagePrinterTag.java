/*
package ar.edu.itba.paw.web.jsp;

import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.helper.MessageHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MessagePrinterTag extends TagSupport implements ApplicationContextAware{

    private Twatt twatt;

    private ApplicationContext applicationContext;

    @Autowired
    private MessageHelper messageHelper;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int doStartTag() throws JspException {
        init();
        try {
            this.sessionFactory.getCurrentSession().beginTransaction();
            JspWriter output = pageContext.getOut();
            String preparedMessage = messageHelper.prepareMessage(((HttpServletRequest)pageContext.getRequest()).getContextPath(), twatt.getMessage());
            output.print(preparedMessage);
            this.sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (IOException e) {
            this.sessionFactory.getCurrentSession().getTransaction().rollback();
            throw new JspException(e.getMessage(), e);
        }
        return EVAL_PAGE;
    }

    private void init() {
        this.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()));
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        factory.autowireBean(this);

    }

    public Twatt getTwatt() {
        return this.twatt;
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
*/
