package ar.edu.itba.paw.helper.implementations;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.model.Url;
import ar.edu.itba.paw.model.database.UrlDAO;
import ar.edu.itba.paw.model.database.implamentations.UrlDAOImpl;
import com.google.common.base.Strings;

public class MessageHelperImpl implements MessageHelper {

    private static MessageHelperImpl instance;
    public UrlDAO urlmanager;
    private Pattern urlPattern;
    private Pattern hashtagPattern;

    private MessageHelperImpl() {
        urlmanager = UrlDAOImpl.getInstance();
        urlPattern = Pattern
                .compile("/s/[a-z0-9]{5}");
        hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
    }

    public static MessageHelper getInstance() {
        if (instance == null) {
            instance = new MessageHelperImpl();
        }
        return instance;
    }

	public String shorten(String url) {
        if (Strings.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("Invalid URL");
        }
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

	public String prepareMessage(String message) {
        if (Strings.isNullOrEmpty(message)) {
            throw new IllegalArgumentException("Invalid Message received");
        }
		Matcher urlMatcher = urlPattern.matcher(message);
		while (urlMatcher.find()) {
			String url = urlMatcher.group();
			message = message.replace(url, "<a href=\"" + url + "\">"
					+ url + "</a>");
		}
        Matcher hashtagMatcher = hashtagPattern.matcher(message);
        while (hashtagMatcher.find()) {
            String hashtag = hashtagMatcher.group();
            message = message.replace(hashtag, "<a href=\"/hashtag/"+hashtag.trim().split("#")[1]+"\">"+hashtag+"</a>");
        }
		return message;
	}
}
