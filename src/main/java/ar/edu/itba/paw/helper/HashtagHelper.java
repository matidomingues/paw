package ar.edu.itba.paw.helper;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 12/09/13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public interface HashtagHelper {

    void create(Hashtag hashtag);
    void resolveHashtags(Twatt twatt);
    Hashtag getHashtag(String hashtag);
    List<Hashtag> getHashtags(Twatt twatt);
    List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime);
    void relate(Hashtag hashtag, Twatt twatt);
}
