package ar.edu.itba.paw.web.pages.login;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.paw.web.pages.base.BasePage;


public class LoginPage extends BasePage {
	
	private static final long serialVersionUID = 5423510581714946914L;
	
	@SuppressWarnings("serial")
	public LoginPage() {
		add(new SignInPanel("signIn", true));
		add(new Link<Void>("register2") {
			@Override
			public void onClick() {
				setResponsePage(RegisterPage.class);
			}
		});
		add(new Link<Void>("recoverPassword") {
			@Override
			public void onClick() {
				setResponsePage(PasswordRetrival.class);
			}
		});
	}
}