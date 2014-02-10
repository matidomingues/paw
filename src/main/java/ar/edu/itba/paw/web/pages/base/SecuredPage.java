package ar.edu.itba.paw.web.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.common.base.Strings;

import ar.edu.itba.paw.domain.entity.EntityModel;
import ar.edu.itba.paw.domain.twatt.TwattRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.web.TwatterSession;
import ar.edu.itba.paw.web.pages.login.LoginPage;
import ar.edu.itba.paw.web.pages.user.FavouritePage;
import ar.edu.itba.paw.web.pages.user.FindPage;
import ar.edu.itba.paw.web.pages.user.NotificationPage;
import ar.edu.itba.paw.web.pages.user.ProfilePage;
import ar.edu.itba.paw.web.pages.user.ReportPage;
import ar.edu.itba.paw.web.pages.user.SettingsPage;

public abstract class SecuredPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3219118939054572439L;

	@SpringBean UserRepo userRepo;
	
	@SpringBean TwattRepo twattRepo;
	
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
				setResponsePage(new SettingsPage());
			}
		});
		
		add(new Link<Void>("find") {
			@Override
			public void onClick() {
				setResponsePage(new FindPage(""));
			}
		});
		
		add(new Link<Void>("notifications") {
			@Override
			public void onClick() {
				setResponsePage(new NotificationPage(getViewer()));				
			}
		});
		
		add(new Link<Void>("favourites") {
			@Override
			public void onClick() {
				setResponsePage(new FavouritePage(getViewer()));				
			}
		});
		
		add(new Link<Void>("profile") {
			@Override
			public void onClick() {
				setResponsePage(new ProfilePage(getViewer()));
			}
		});
		
		add(new Link<Void>("report") {
			@Override
			public void onClick() {
				setResponsePage(new ReportPage());
			}
		});

		add(new TwattForm("twattForm", getViewer()));
	}

	protected TwatterSession getTwatterSession() {
		return (TwatterSession) getSession();
	}
	
	protected IModel<TwattUser> getViewer() {
		TwattUser viewer = null;
		if (!Strings.isNullOrEmpty(getTwatterSession().getUsername())) { viewer = userRepo.getUserByUsername(getTwatterSession().getUsername()); }
		return new EntityModel<TwattUser>(TwattUser.class, viewer);
	}
}