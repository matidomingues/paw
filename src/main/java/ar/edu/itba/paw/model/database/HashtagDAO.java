package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import org.joda.time.DateTime;

import java.util.List;

public interface HashtagDAO {

    void create(Hashtag hashtag);

    Hashtag find(String hashtag);

    Hashtag find(int id);

    void relate(Hashtag hashtag, Twatt twatt);

    List<Hashtag> findForTwatt(Twatt twatt);

    List<Hashtag> findForTwatt(int id);

    List<Hashtag> findTrendingHashtagsAfter(DateTime dateTime);

    int getMentionsAfter(Hashtag hashtag, DateTime filterDate);
}
