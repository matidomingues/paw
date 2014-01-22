package ar.edu.itba.paw.web.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.paw.web.pages.login.LoginPage;


public class BasePage extends WebPage {

	public BasePage() {
		add(new Link<Void>("login") {
			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		});
	}
}

