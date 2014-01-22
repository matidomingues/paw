package ar.edu.itba.paw.web.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.paw.web.pages.login.LoginPage;
import ar.edu.itba.paw.web.pages.login.RegisterPage;


public class BasePage extends WebPage {

	public BasePage() {
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

