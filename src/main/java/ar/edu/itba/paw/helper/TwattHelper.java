package ar.edu.itba.paw.helper;

import java.util.List;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;

public interface TwattHelper {

	public void addTwatt(Twatt twatt);

	public List<Twatt> getTwattsByUsername(String username);

	public boolean isValidTwatt(Twatt twatt);

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag);

    Twatt getTwatt(int twatt_id);

    void delete(Twatt twatt);
}