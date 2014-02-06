package ar.edu.itba.paw.domain.url;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;

import com.google.common.base.Strings;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class HibernateUrlRepo extends AbstractHibernateRepo<Url> implements UrlRepo {

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
        return this.find(data).getResol();
	}
	
	private boolean baseExists(String base){
        if (Strings.isNullOrEmpty(base)) {
            throw new IllegalArgumentException("Base is null or empty");
        }
		return !find("from Url where base = ?", base).isEmpty();
	}

	@Override
	public Url find(String shortUrl) {
        if (Strings.isNullOrEmpty(shortUrl)) {
            throw new IllegalArgumentException("Data is null or empty");
        }

        List<Url> urls = find("from Url where base = ?", shortUrl);
        if (urls.isEmpty()) {
            return null;
        }
		return urls.get(0);
	}

}
