package ar.edu.itba.paw.model.database.implamentations;

import java.util.HashSet;
import java.util.Set;

import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.database.HashtagDAO;

public class HashtagDAOImpl implements HashtagDAO {
	
	private static HashtagDAO instance = null;

	private HashtagDAOImpl(){
		
	}
	
	public static HashtagDAO getInstance(){
		if(instance == null){
			instance = new HashtagDAOImpl();
		}
		return instance;
	}
	
    public Set<Hashtag> getTrendingHashtags(){
		return new HashSet<Hashtag>();
	}
}

