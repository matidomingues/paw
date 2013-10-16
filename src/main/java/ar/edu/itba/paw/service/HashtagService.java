package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import org.joda.time.DateTime;

import java.util.List;

public interface HashtagService {

    void create(Hashtag hashtag);
    void resolveHashtags(Twatt twatt);
    Hashtag getHashtag(String hashtag);
    List<Hashtag> getHashtags(Twatt twatt);
    List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime);
    void relate(Hashtag hashtag, Twatt twatt);
    int getMentions(Hashtag hashtag, DateTime filterDate);
    boolean isValid(Hashtag hashtag);
}
