package ar.edu.itba.paw.manager;

import java.util.HashSet;
import java.util.UUID;

import ar.edu.itba.paw.model.Url;

public class UrlDAO {

	private static UrlDAO instance = null;
	private HashSet<Url> urls = new HashSet<Url>();
	
	private UrlDAO(){
		
	}
	
	public static synchronized UrlDAO getInstance(){
		if(instance == null){
			instance = new UrlDAO();
		}
		return instance;
	}
	
	private Url findBase(String base){
		for(Url url: urls){
			if(url.getBase().compareTo(base) == 0){
				return url;
			}
		}
		return null;
	}
	
	public String resolve(String base){
		Url url = findBase(base);
		if(url != null){
			return url.getResol();
		}
		return null;
	}
	
	public Url reverseUrl(String resol){
		for(Url url: urls){
			if(url.getResol().compareTo(resol) == 0){
				return url;
			}
		}
		return null;
	}
	
	public void addRoute(String base, String resol){
		urls.add(new Url(base, resol));
	}
}
