package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Hashtag;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface HashtagDAO {
    Set<Hashtag> getTrendingHashtags();
}
