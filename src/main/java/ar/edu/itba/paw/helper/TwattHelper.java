package ar.edu.itba.paw.helper;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;

import java.util.List;

public interface TwattHelper {

	public void addTwatt(Twatt twatt);

	public List<Twatt> getTwattsByUsername(String username);

	public boolean isValidTwatt(Twatt twatt);

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag);

    Twatt getTwatt(int twatt_id);

    void delete(Twatt twatt);
}