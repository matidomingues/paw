package ar.edu.itba.paw.repository.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;

import ar.edu.itba.paw.entity.Hashtag;
import ar.edu.itba.paw.entity.User;
import ar.edu.itba.paw.entity.Twatt;
import ar.edu.itba.paw.repository.HashtagRepo;
import ar.edu.itba.paw.repository.TwattRepo;
import ar.edu.itba.paw.repository.UrlRepo;
import ar.edu.itba.paw.repository.UserRepo;

public class HibernateTwattRepo extends AbstractHibernateRepo implements TwattRepo{

	private UserRepo userRepo;
	private HashtagRepo hastagRepo;
	private UrlRepo urlRepo;
	
	@Autowired
	public HibernateTwattRepo(SessionFactory sessionFactory, UserRepo userRepo, HashtagRepo hastagRepo, UrlRepo urlRepo) {
		super(sessionFactory);
		this.userRepo = userRepo;
		this.hastagRepo = hastagRepo;
		this.urlRepo = urlRepo;
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
			newurl = urlRepo.shorten(oldurl);
			message = message.replace(oldurl, newurl);
		}
		return message;
	}

	public void addTwatt(Twatt twatt) {
		if (this.isValidTwatt(twatt) && !twatt.isDeleted()) {
			twatt.setMessage(shortenUrls(twatt.getMessage()));
			twatt.getCreator().addTwatt(twatt);
			save(twatt);
            hastagRepo.resolveHashtags(twatt);
		} else {
            throw new IllegalArgumentException("Invalid twatt");
        }
	}

	public List<Twatt> getTwattsByUsername(String username) {
        if (Strings.isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Invalid username");
        }
		User user = userRepo.getUserByUsername(username);
		if (user != null) {
			return user.getTwatts();

		}
		return new LinkedList<Twatt>();
	}


    public boolean isValidTwatt(Twatt twatt) {
        return twatt != null &&
                twatt.getCreator() != null &&
                twatt.getCreator().isValidUser() &&
                !Strings.isNullOrEmpty(twatt.getMessage()) &&
                twatt.getTimestamp() != null;
    }

    public List<Twatt> getTwattsByHashtag(Hashtag hashtag) {
        if (!hashtag.isValid()) {
            throw new IllegalArgumentException("Invalid hashtag");
        }
        return hashtag.getTwatts();
    }

    public Twatt getTwatt(int twatt_id) {
        if (twatt_id <= 0) {
            throw  new IllegalArgumentException("Invalid id");
        }
        return get(Twatt.class, twatt_id);
    }

    public void delete(Twatt twatt) {
        twatt.setDeleted();
        save(twatt);
    }

}
