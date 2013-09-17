package ar.edu.itba.paw.helper.implementations;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.model.Url;
import ar.edu.itba.paw.model.database.UrlDAO;
import ar.edu.itba.paw.model.database.implamentations.UrlDAOImpl;

public class MessageHelperImpl implements MessageHelper {

	public static UrlDAO urlmanager = UrlDAOImpl.getInstance();

	public String shorten(String url) {
		String newurl = "/s/";
		Url reverse = urlmanager.reverseUrl(url);
		if (reverse != null) {
			newurl = newurl.concat(reverse.getBase());
		} else {
			String base = UUID.randomUUID().toString().substring(0, 5);
			newurl = newurl.concat(base);
			urlmanager.addRoute(base, url);
		}
		return newurl;
	}

	public static String prepareMessage(String message) {
		Pattern urlPattern = Pattern
				.compile("/s/[a-z0-9]{5}");
		Matcher urlMatcher = urlPattern.matcher(message);
		while (urlMatcher.find()) {
			String url = urlMatcher.group();
			message = message.replace(url, "<a href=\"" + url + "\">"
					+ url + "</a>");
		}
		return message;
	}
}
