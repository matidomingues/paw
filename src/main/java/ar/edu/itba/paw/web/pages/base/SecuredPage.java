package ar.edu.itba.paw.web.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.pages.login.LoginPage;

public abstract class SecuredPage extends WebPage {

	public SecuredPage() {
		TwatterSession session = getTwatterSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new LoginPage());
			
		}

		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				getTwatterSession().signOut();
				setResponsePage(getApplication().getHomePage());
			}
		});
	}

	protected TwatterSession getTwatterSession() {
		return (TwatterSession) getSession();
	}
}