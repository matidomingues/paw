package ar.edu.itba.paw.hibernate.repository;

import java.util.List;

import ar.edu.itba.paw.hibernate.entity.Hashtag;
import ar.edu.itba.paw.hibernate.entity.Twatt;

public interface TwattRepo {
	
	public void addTwatt(Twatt twatt);

	public List<Twatt> getTwattsByUsername(String username);

	public boolean isValidTwatt(Twatt twatt);

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag);

    Twatt getTwatt(int twatt_id);

    void delete(Twatt twatt);
}
