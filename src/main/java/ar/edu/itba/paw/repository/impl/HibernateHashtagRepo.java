package ar.edu.itba.paw.repository.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import com.google.common.base.Strings;

import ar.edu.itba.paw.entity.Hashtag;
import ar.edu.itba.paw.entity.Twatt;
import ar.edu.itba.paw.repository.HashtagRepo;

public class HibernateHashtagRepo extends AbstractHibernateRepo implements
		HashtagRepo {
	
	private Pattern hashtagPattern = Pattern
            .compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");

	
	public HibernateHashtagRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void create(Hashtag hashtag) {
		if (!hashtag.isValid()) {
			throw new IllegalArgumentException("Invalid Hashtag");
		}
		save(hashtag);
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
		return (Hashtag)find("from Hashtag where tagName = ?", hashtag).get(0);
	}

	public List<Hashtag> getHashtags(Twatt twatt) {
		if (twatt == null) {
			throw new IllegalArgumentException("Invalid twatt");
		}
		return twatt.getHashtags();
	}

	public List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime) {
		if (dateTime == null || dateTime.isAfterNow()) {
			throw new IllegalArgumentException("Invalid Date");
		}
		return find("from Hashtag where date > ?", dateTime);
	}

	public void relate(Hashtag hashtag, Twatt twatt) {
		if (!hashtag.isValid() || twatt == null) {
			throw new IllegalArgumentException(
					"Invalid Hashtag or Twatt received");
		}
		twatt.addHashtag(hashtag);
		hashtag.addTwatt(twatt);
	}

	public int getMentions(Hashtag hashtag, DateTime filterDate) {
		if (!hashtag.isValid() || filterDate == null
				|| filterDate.isAfterNow()) {
			throw new IllegalArgumentException("Invalid Hashtag or filter date");
		}
		//TODO: filtrar por fecha
		return hashtag.getTwatts().size();
	}

}
