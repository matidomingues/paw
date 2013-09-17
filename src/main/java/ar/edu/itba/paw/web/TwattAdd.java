package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.utils.exceptions.InvalidUserException;import ar.edu.itba.paw.utils.exceptions.MessageEmptyException;
import com.google.common.base.Strings;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class TwattAdd extends HttpServlet{

	private TwattHelper twattmanager = new TwattHelper();
    private UserHelper userHelper = new UserHelper();

	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String message = req.getParameter("message");
        UUID userUUID = getSessionFromCookie(req.getCookies());

        if (Strings.isNullOrEmpty(message)) {
            throw new MessageEmptyException();
        }

        User user = null;

        if (userUUID == null || (user = this.userHelper.getUserBySession(userUUID)) == null) {
            throw new InvalidUserException();
        }


        twattmanager.addTwatt(new Twatt(user, message, false, DateTime.now()));

		resp.sendRedirect("home");
	}
}
