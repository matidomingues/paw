package ar.edu.itba.paw.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import ar.edu.itba.paw.helper.implementations.HashtagHelperImpl;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.TwattDAO;
import ar.edu.itba.paw.model.database.implamentations.TwattDAOImpl;
import ar.edu.itba.paw.utils.exceptions.InvalidTwattException;
import com.google.common.base.Strings;

public class TwattHelper {

	private UserHelper usermanager;
	private TwattDAO twattDAO;
    private HashtagHelper hashtagHelper;
    private Pattern hashtagPattern;

    public TwattHelper() {
        this.hashtagHelper = HashtagHelperImpl.getInstance();
        this.usermanager = new UserHelper();
        this.twattDAO = TwattDAOImpl.getInstance();
        this.hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
    }

	public void addTwatt(Twatt twatt) {
		if (this.isValidTwatt(twatt) && !twatt.isDeleted() && twatt.getTimestamp().isBeforeNow() ) {
			this.twattDAO.create(twatt);
            this.hashtagHelper.resolveHashtags(twatt);
		} else {
            throw new InvalidTwattException();
        }
	}

	public List<Twatt> getTwattsByUUID(UUID uuid) {
		User user = usermanager.getUserBySession(uuid);
		if (user != null) {
			return prepareHrefLink(twattDAO.findByUser(user));
		}
		return new LinkedList<Twatt>();
	}

	public List<Twatt> getTwattsByUsername(String username) {
		User user = usermanager.getUserByUsername(username);
		if (user != null) {
			return prepareHrefLink(twattDAO.findByUser(user));
		}
		return new LinkedList<Twatt>();
	}

	public List<Twatt> prepareHrefLink(List<Twatt> twatts) {
		for (Twatt twatt : twatts) {
			twatt.setMessage(MessageHelper.prepareMessage(twatt.getMessage()));
		}
		return twatts;
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
