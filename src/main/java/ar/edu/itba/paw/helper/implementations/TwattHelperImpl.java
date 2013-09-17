package ar.edu.itba.paw.helper.implementations;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.helper.HashtagHelper;
import ar.edu.itba.paw.helper.MessageHelper;
import ar.edu.itba.paw.helper.TwattHelper;
import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.TwattDAO;
import ar.edu.itba.paw.model.database.implamentations.TwattDAOImpl;
import ar.edu.itba.paw.utils.exceptions.InvalidTwattException;
import com.google.common.base.Strings;

public class TwattHelperImpl implements TwattHelper {

	private MessageHelper messagehelper;
	private UserHelper usermanager;
	private TwattDAO twattDAO;
	private HashtagHelper hashtagHelper;
	private Pattern hashtagPattern;
	
	public TwattHelperImpl() {
	    this.hashtagHelper = HashtagHelperImpl.getInstance();
	    this.usermanager = new UserHelperImpl();
	    this.twattDAO = TwattDAOImpl.getInstance();
	    this.hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
	    this.messagehelper = new MessageHelperImpl();
	}

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

	public void addTwatt(Twatt twatt) {
		if (this.isValidTwatt(twatt) && !twatt.isDeleted() && twatt.getTimestamp().isBeforeNow() ) {
			twatt.setMessage(shortenUrls(twatt.getMessage()));
			this.twattDAO.create(twatt);
            this.hashtagHelper.resolveHashtags(twatt);
		} else {
            throw new InvalidTwattException();
        }
	}

	public List<Twatt> getTwattsByUsername(String username) {
		User user = usermanager.getUserByUsername(username);
		if (user != null) {
			return twattDAO.findByUser(user);

		}
		return new LinkedList<Twatt>();
	}


    public boolean isValidTwatt(Twatt twatt) {
        return twatt != null &&
                twatt.getCreator() != null &&
                usermanager.isValidUser(twatt.getCreator()) &&
                Strings.isNullOrEmpty(twatt.getMessage()) &&
                twatt.getTimestamp() != null;
    }

    public List<Twatt> getTwattsByHashtag(Hashtag hashtag) {
        return this.twattDAO.findByHashtag(hashtag);
    }
}
