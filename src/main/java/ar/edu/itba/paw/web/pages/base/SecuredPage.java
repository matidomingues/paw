package ar.edu.itba.paw.web.pages.base;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.pages.login.LoginPage;
import ar.edu.itba.paw.web.pages.user.FindPage;
import ar.edu.itba.paw.web.pages.user.ProfilePage;
import ar.edu.itba.paw.web.pages.user.SettingsPage;

public abstract class SecuredPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3219118939054572439L;

	@SpringBean
	private UserRepo userRepo;
	
	@SpringBean
	private TwattRepo twattRepo;
	
	private transient String message;
	
	
	@SuppressWarnings("serial")
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
		
		add(new Link<Void>("home") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
		
		add(new Link<Void>("settings") {
			@Override
			public void onClick() {
				TwattUser user = userRepo.getUserByUsername(getTwatterSession().getUsername());
				setResponsePage(new SettingsPage(user));
			}
		});
		
		add(new Link<Void>("find") {
			@Override
			public void onClick() {
				setResponsePage(new FindPage("", getViewer()));
			}
		});
		
		add(new Link<Void>("profile") {
			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(getViewer(), getViewer()));
			}
		});
		
//		Form<SecuredPage> form = new Form<SecuredPage>("twattForm") {
//			@Override
//			protected void onSubmit() {
//				TwattUser user = userRepo.getUserByUsername(getTwatterSession().getUsername());
//				twattRepo.create(new Twatt(message, user));
//				setResponsePage(new ProfilePage(user));
//			}
//		};
//		TextField<String> messageField = new TextField<String>("message");
//		messageField.setRequired(true);
//		form.add(messageField);
//		form.add(new Button("twatt", new ResourceModel("Twatt!")));
//		add(form);
		add(new TwattForm("twattForm"));
	}

	protected TwatterSession getTwatterSession() {
		return (TwatterSession) getSession();
	}
	
	protected IModel<TwattUser> getViewer() {
		return new EntityModel<TwattUser>(TwattUser.class, userRepo.getUserByUsername(getTwatterSession().getUsername()));
	}
	
	@SuppressWarnings("serial")
	private class TwattForm extends Form<Void> {
		
		private String message;
		private TextField<String> messageField;

		public TwattForm(String id) {
			super(id);
			messageField = new TextField<String>("message", new PropertyModel<String>(this, "message"));
			messageField.setRequired(true);
			add(messageField);
			add(new Button("twatt", new ResourceModel("Twatt!")));
		}
		
		@Override
		protected void onSubmit() {
			TwattUser user = userRepo.getUserByUsername(getTwatterSession().getUsername());
			twattRepo.create(new Twatt(message, user));
			setResponsePage(new ProfilePage(SecuredPage.this.getViewer(), SecuredPage.this.getViewer()));
		}
		
	}
}