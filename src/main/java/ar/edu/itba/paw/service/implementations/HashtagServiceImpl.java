package ar.edu.itba.paw.service.implementations;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.database.HashtagDAO;
import ar.edu.itba.paw.model.database.implementations.HashtagDAOImpl;
import ar.edu.itba.paw.service.HashtagService;

import com.google.common.base.Strings;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HashtagServiceImpl implements HashtagService {

	private HashtagDAO hashtagDAO;
	private Pattern hashtagPattern;

	@Autowired
	public HashtagServiceImpl(HashtagDAO hastagDAO) {
		this.hashtagDAO = hastagDAO;
		this.hashtagPattern = Pattern
				.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
	}

	public void create(Hashtag hashtag) {
		if (!this.isValid(hashtag)) {
			throw new IllegalArgumentException("Invalid Hashtag");
		}
		this.hashtagDAO.create(hashtag);
	}

	public void resolveHashtags(Twatt twatt) {
		Matcher hashtagMatcher = this.hashtagPattern
				.matcher(twatt.getMessage());
		while (hashtagMatcher.find()) {
			String hashtagName = hashtagMatcher.group().trim().split("#")[1];
			Hashtag hashtag = null;
			if ((hashtag = this.getHashtag(hashtagName)) == null) {
				hashtag = new Hashtag(twatt, hashtagName);
				this.create(hashtag);
				hashtag = this.getHashtag(hashtag.getTagName());
			}
			this.relate(hashtag, twatt);
		}
	}

	public Hashtag getHashtag(String hashtag) {
		if (Strings.isNullOrEmpty(hashtag)) {
			throw new IllegalArgumentException("Invalid hashtag");
		}
		return this.hashtagDAO.find(hashtag);
	}

	public List<Hashtag> getHashtags(Twatt twatt) {
		if (twatt == null) {
			throw new IllegalArgumentException("Invalid twatt");
		}
		return this.hashtagDAO.findForTwatt(twatt);
	}

	public List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime) {
		if (dateTime == null || dateTime.isAfterNow()) {
			throw new IllegalArgumentException("Invalid Date");
		}
		return this.hashtagDAO.findTrendingHashtagsAfter(dateTime);
	}

	public void relate(Hashtag hashtag, Twatt twatt) {
		if (!this.isValid(hashtag) || twatt == null) {
			throw new IllegalArgumentException(
					"Invalid Hashtag or Twatt received");
		}
		this.hashtagDAO.relate(hashtag, twatt);
	}

	public int getMentions(Hashtag hashtag, DateTime filterDate) {
		if (!this.isValid(hashtag) || filterDate == null
				|| filterDate.isAfterNow()) {
			throw new IllegalArgumentException("Invalid Hashtag or filter date");
		}
		return this.hashtagDAO.getMentionsAfter(hashtag, filterDate);
	}

	public boolean isValid(Hashtag hashtag) {
		return !Strings.isNullOrEmpty(hashtag.getTagName())
				&& hashtag.getFirstTweet() != null;

	}

}
