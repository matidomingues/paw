package ar.edu.itba.paw.domain.twatt;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

import java.util.List;

public interface TwattRepo {
	
	public void addTwatt(Twatt twatt);

	public List<Twatt> getTwattsByUsername(String username);

	public boolean isValidTwatt(Twatt twatt);

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag);

    Twatt getTwatt(int twatt_id);

    void delete(Twatt twatt);
    
    List<Twatt> getTwattsByFollowings(TwattUser user);
}
