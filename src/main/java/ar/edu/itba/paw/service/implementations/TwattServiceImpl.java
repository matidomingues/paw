package ar.edu.itba.paw.service.implementations;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.TwattDAO;
import ar.edu.itba.paw.service.HashtagService;
import ar.edu.itba.paw.service.TwattService;
import ar.edu.itba.paw.service.UrlService;
import ar.edu.itba.paw.service.UserService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TwattServiceImpl implements TwattService {

	private UrlService urlService;
	private UserService usermanager;
	private TwattDAO twattDAO;
	private HashtagService hashtagHelper;

	@Autowired
	public TwattServiceImpl(TwattDAO twattDAO, HashtagService hastagService, UserService userService, UrlService urlService) {
	    this.usermanager = userService;
	    this.twattDAO = twattDAO;
	    this.urlService = urlService;
	    this.hashtagHelper = hastagService;
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
			newurl = urlService.shorten(oldurl);
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

    public Twatt getTwatt(int twatt_id) {
        if (twatt_id <= 0) {
            throw  new IllegalArgumentException("Invalid id");
        }
        return this.twattDAO.find(twatt_id);
    }

    public void delete(Twatt twatt) {
        twatt.setDeleted();
        this.twattDAO.update(twatt);
    }
}
