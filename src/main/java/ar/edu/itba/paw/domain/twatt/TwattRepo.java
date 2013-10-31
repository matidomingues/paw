package ar.edu.itba.paw.domain.twatt;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.utils.Report;

public interface TwattRepo {
	
	public void addTwatt(Twatt twatt);

	public List<Twatt> getTwattsByUsername(String username);

	public boolean isValidTwatt(Twatt twatt);

	public List<Twatt> getTwattsByHashtag(Hashtag hashtag);

    Twatt getTwatt(int twatt_id);

    void delete(Twatt twatt);
    
    List<Twatt> getTwattsByFollowings(TwattUser user);
    
    public List<Report> getTwattReportByDate(TwattUser user, DateTime startDate, DateTime endDate, String days);
}
