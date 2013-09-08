package ar.edu.itba.paw.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import ar.edu.itba.paw.objects.Url;

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
			System.out.println(base);
			System.out.println(url.getBase());
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
	
	public String shorten(String url){
		String newurl = "/s/";
		String base = UUID.randomUUID().toString().substring(0, 5);
		newurl = newurl.concat(base);
		urls.add(new Url(base, url));
		return newurl;
	}
}
