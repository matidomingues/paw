package ar.edu.itba.paw.web;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;

@SuppressWarnings("serial")
public class TwatterSession extends AuthenticatedWebSession {
	
	private String username;

	public static TwatterSession get() {
		return (TwatterSession) Session.get();
	}

	public TwatterSession(Request request) {
		super(request);
	}

	public String getUsername() {
		return username;
	}

	public boolean signIn(String username, String password, UserRepo users) {
		TwattUser user = users.getUserByUsername(username);
		if (user != null && user.checkPassword(password)) {
			this.username = username;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean authenticate(String username, String password) {
		TwattUser user = ((TwatterApp)getApplication()).getUserRepository().getUserByUsername(username);
		if (user != null && user.checkPassword(password)) {
			this.username = username;
			return true;
		}
		return false;
	}

	public void signOut() {
        clear();
	}

	@Override
	public Roles getRoles() {
		// TODO Auto-generated method stub
		return null;
	}
}
