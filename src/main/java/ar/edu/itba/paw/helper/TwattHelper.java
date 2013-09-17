package ar.edu.itba.paw.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.TwattDAO;
import ar.edu.itba.paw.model.database.implamentations.TwattDAOImpl;

public class TwattHelper {

	private UserHelper usermanager = new UserHelper();
	private TwattDAO twattmanager = TwattDAOImpl.getInstance();
	private MessageHelper messagehelper = new MessageHelper();

	private String shortenUrls(String message) {
		Pattern urlPattern = Pattern
				.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");
		Matcher urlMatcher = urlPattern.matcher(message);
		String newurl;
		while (urlMatcher.find()) {
			String oldurl = urlMatcher.group();
			newurl = messagehelper.shorten(oldurl);
			message = message.replace(oldurl, newurl);
		}
		return message;
	}

	public boolean addTwatt(User user, String message) {
		message = shortenUrls(message);
		return twattmanager.addTwatt(user, message);
	}

	public List<Twatt> getTwatts(User user) {
		return twattmanager.getTwattsByUser(user);
	}

	public List<Twatt> getTwattsByUsername(String username) {
		User user = usermanager.getUserByUsername(username);
		if (user != null) {
			twattmanager.getTwattsByUser(user);
		}
		return new LinkedList<Twatt>();
	}


}
