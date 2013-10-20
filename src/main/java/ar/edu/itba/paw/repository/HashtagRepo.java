package ar.edu.itba.paw.repository;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.entity.Hashtag;
import ar.edu.itba.paw.entity.Twatt;


public interface HashtagRepo {

	void create(Hashtag hashtag);

	void resolveHashtags(Twatt twatt);

	Hashtag getHashtag(String hashtag);

	List<Hashtag> getHashtags(Twatt twatt);

	List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime);

	void relate(Hashtag hashtag, Twatt twatt);

	int getMentions(Hashtag hashtag, DateTime filterDate);

}
