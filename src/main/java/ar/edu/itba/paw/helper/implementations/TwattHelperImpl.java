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

    private static TwattHelper instance;
	private MessageHelper messagehelper;
	private UserHelper usermanager;
	private TwattDAO twattDAO;
	private HashtagHelper hashtagHelper;
	private Pattern hashtagPattern;

    public static TwattHelper getInstance() {
        if (instance == null) {
            instance = new TwattHelperImpl();
        }
        return instance;
    }

	private TwattHelperImpl() {
	    this.hashtagHelper = HashtagHelperImpl.getInstance();
	    this.usermanager = UserHelperImpl.getInstance();
	    this.twattDAO = TwattDAOImpl.getInstance();
	    this.hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
	    this.messagehelper = MessageHelperImpl.getInstance();
	}

	private String shortenUrls(String message) {
        if (Strings.isNullOrEmpty(message)) {
            throw new IllegalArgumentException("Invalid message received");
        }
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
		if (this.isValidTwatt(twatt) && !twatt.isDeleted() ) {
			twatt.setMessage(shortenUrls(twatt.getMessage()));
			this.twattDAO.create(twatt);
            this.hashtagHelper.resolveHashtags(this.twattDAO.find(twatt));
		} else {
            throw new IllegalArgumentException("Invalid twatt");
        }
	}

	public List<Twatt> getTwattsByUsername(String username) {
        if (Strings.isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Invalid username");
        }
		User user = usermanager.getUserByUsername(username);
		if (user != null) {
			return twattDAO.find(user);

		}
		return new LinkedList<Twatt>();
	}


    public boolean isValidTwatt(Twatt twatt) {
        return twatt != null &&
                twatt.getCreator() != null &&
                usermanager.isValidUser(twatt.getCreator()) &&
                !Strings.isNullOrEmpty(twatt.getMessage()) &&
                twatt.getTimestamp() != null;
    }

    public List<Twatt> getTwattsByHashtag(Hashtag hashtag) {
        if (!this.hashtagHelper.isValid(hashtag)) {
            throw new IllegalArgumentException("Invalid hashtag");
        }
        return this.twattDAO.find(hashtag);
    }

    @Override
    public Twatt getTwatt(int twatt_id) {
        if (twatt_id <= 0) {
            throw  new IllegalArgumentException("Invalid id");
        }
        return this.twattDAO.find(twatt_id);
    }

    @Override
    public void delete(Twatt twatt) {
        twatt.setDeleted();
        this.twattDAO.update(twatt);
    }
}
