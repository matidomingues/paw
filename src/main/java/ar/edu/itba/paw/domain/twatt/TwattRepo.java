package ar.edu.itba.paw.domain.twatt;


import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.utils.Report;
import org.joda.time.DateTime;

import java.util.List;

public interface TwattRepo {
	
	public void create(Twatt twatt);

    public void create(Retwatt retwatt);

	public List<Twatt> getTwattsByUsername(String username);

	public boolean isValidTwatt(Twatt twatt);

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag);

    Twatt getTwatt(int twatt_id);

    List<Twatt> getTwattsByFollowings(TwattUser user);
    
    public List<Report> getTwattReportByDate(TwattUser user, DateTime startDate, DateTime endDate, String days);
}
