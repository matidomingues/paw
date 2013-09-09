package ar.edu.itba.paw.manager;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
	
	public List<Hashtag> getTrendingHashtags(){
		return new LinkedList<Hashtag>();
	}
}

