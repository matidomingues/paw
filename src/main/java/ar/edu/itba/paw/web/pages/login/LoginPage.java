package ar.edu.itba.paw.web.pages.login;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.pages.base.BasePage;


public class LoginPage extends BasePage {
	
	@SpringBean
	private UserRepo userRepo;
	
	private transient String username;
	private transient String password;
	
	public LoginPage() {
		add(new FeedbackPanel("feedback"));
		
		Form<LoginPage> form = new Form<LoginPage>("loginForm", new CompoundPropertyModel<LoginPage>(this)) {
			@Override
			protected void onSubmit() {
				TwatterSession session = TwatterSession.get();

				if (session.signIn(username, password, userRepo)) {
					if (!continueToOriginalDestination()) {
						setResponsePage(getApplication().getHomePage());
					}
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("login", new ResourceModel("login")));
		form.add(new Link<Void>("register") {
			@Override
			public void onClick() {
				setResponsePage(RegisterPage.class);
			}
		});
		form.add(new Link<Void>("recoverPassword") {
			@Override
			public void onClick() {
				setResponsePage(PasswordRetrival.class);
			}
		});
		
		add(form);
	}
}