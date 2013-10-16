package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface TwattDAO {

    boolean create(Twatt twatt);

    void update(Twatt twatt);

    Twatt find(int id);

    Twatt find(Twatt twatt);

    List<Twatt> find(Hashtag hashtag);

    List<Twatt> find(User user);
}
