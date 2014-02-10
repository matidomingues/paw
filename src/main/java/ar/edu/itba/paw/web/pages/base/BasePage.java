package ar.edu.itba.paw.web.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import com.google.common.base.Strings;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.web.pages.login.LoginPage;
import ar.edu.itba.paw.web.pages.login.PasswordRetrival;
import ar.edu.itba.paw.web.pages.login.RegisterPage;


public class BasePage extends WebPage {

	private static final long serialVersionUID = -2784821001225116101L;

	@SuppressWarnings("serial")
	public BasePage() {
		add(new Link<Void>("home") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
		add(new Link<Void>("login") {
			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		});
		add(new Link<Void>("register") {
			@Override
			public void onClick() {
				setResponsePage(RegisterPage.class);
			}
		});
	}
	

}

