package ar.edu.itba.paw.domain.hashtag;

import ar.edu.itba.paw.domain.twatt.Twatt;
import org.joda.time.DateTime;

import java.util.List;


public interface HashtagRepo {

    public static final String HASHTAG_REGEX = "(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)";

	void create(Hashtag hashtag);

	void resolveHashtags(Twatt twatt);

	Hashtag getHashtag(String hashtag);

	List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime);

	int getMentions(Hashtag hashtag, DateTime filterDate);

}
