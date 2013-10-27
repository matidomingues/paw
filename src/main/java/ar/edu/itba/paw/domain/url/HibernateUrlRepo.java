package ar.edu.itba.paw.domain.url;

import java.util.List;
import java.util.UUID;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

@Repository
public class HibernateUrlRepo extends AbstractHibernateRepo<Url> implements UrlRepo {

	@Autowired
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
        List<Url> urls = find("from Url where base = ?", data);
        if (urls.isEmpty()) {
            return null;
        }
		return urls.get(0).getResol();
	}
	
	private boolean baseExists(String base){
		return !find("from Url where base = ?", base).isEmpty();
	}

}
