package ar.edu.itba.paw.web;

import ar.edu.itba.paw.helper.HashtagHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.helper.implementations.HashtagHelperImpl;
import com.google.common.base.Strings;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


public class Home extends HttpServlet{
	
	UserHelper usermanager = new UserHelper();
	HashtagHelper hashtagmanager = HashtagHelperImpl.getInstance();

	private UUID getSessionFromCookie(Cookie[] cookies){
		for(Cookie cookie: cookies){
			if(cookie.getName().compareTo("TwitterUUID") == 0){
				return UUID.fromString(cookie.getValue());
			}
		}
		return null;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String sDays = req.getParameter("days");
        int days = 7;
        try {
            if (!Strings.isNullOrEmpty(sDays)) {
                days = Integer.parseInt(sDays);
            }
        } catch (Exception e) {

        }
		req.setAttribute("hashtags", hashtagmanager.getTrendingsHashtagsAfter(DateTime.now().minusDays(days)));
		req.setAttribute("user", usermanager.getUserBySession(getSessionFromCookie(req.getCookies())));
		req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
	}

}
