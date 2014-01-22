package ar.edu.itba.paw.domain.hashtag;

import ar.edu.itba.paw.domain.twatt.Twatt;
import org.joda.time.DateTime;

import java.util.List;


public interface HashtagRepo {

	void create(Hashtag hashtag);

	void resolveHashtags(Twatt twatt);

	Hashtag getHashtag(String hashtag);

	List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime);

	int getMentions(Hashtag hashtag, DateTime filterDate);

}
