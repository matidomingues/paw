package ar.edu.itba.paw.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;

public class TwatterSession extends WebSession {

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

	public boolean isSignedIn() {
		return username != null;
	}

	public void signOut() {
        invalidate();
        clear();
	}
}
