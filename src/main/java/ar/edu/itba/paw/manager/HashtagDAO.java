package ar.edu.itba.paw.manager;

import java.util.HashSet;
import java.util.Set;

import ar.edu.itba.paw.model.Hashtag;

public class HashtagDAO {
	
	private static HashtagDAO instance = null;

	private HashtagDAO(){
		
	}
	
	public static HashtagDAO getInstance(){
		if(instance == null){
			instance = new HashtagDAO();
		}
		return instance;
	}
	
	public Set<Hashtag> getTrendingHashtags(){
		return new HashSet<Hashtag>();
	}
}

