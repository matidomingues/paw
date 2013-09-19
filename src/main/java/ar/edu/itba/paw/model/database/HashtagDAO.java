package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
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
