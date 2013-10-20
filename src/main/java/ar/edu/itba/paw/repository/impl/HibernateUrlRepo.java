package ar.edu.itba.paw.repository.impl;

import java.util.UUID;

import org.hibernate.SessionFactory;

import com.google.common.base.Strings;

import ar.edu.itba.paw.entity.Url;
import ar.edu.itba.paw.repository.UrlRepo;

public class HibernateUrlRepo extends AbstractHibernateRepo implements UrlRepo {

	public HibernateUrlRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public String shorten(String url) {
		if (Strings.isNullOrEmpty(url)) {
			throw new IllegalArgumentException("Invalid URL");
		}
		String newurl = "/s/";
		
		if (baseExists(url)) {
			newurl = newurl.concat(((Url)find("from Url where resol = ?", url).get(0)).getBase());
		} else {
			String base = UUID.randomUUID().toString().substring(0, 5);
			newurl = newurl.concat(base);
			save(new Url(base, url));
		}
		return newurl;
	}

	public String resolve(String data) {
		return ((Url)(find("from Url where base = ?", data).get(0))).getResol();
	}
	
	private boolean baseExists(String base){
		return !find("from Url where base = ?", base).isEmpty();
	}

}
