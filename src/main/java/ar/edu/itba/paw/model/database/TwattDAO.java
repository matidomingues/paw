package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface TwattDAO {

    boolean create(Twatt twatt);

    void update(Twatt twatt);

    Twatt find(int id);

    List<Twatt> findByHashtag(Hashtag hashtag);

    List<Twatt> findByUser(User user);
}
