package ar.edu.itba.paw.service.implementations;

import ar.edu.itba.paw.model.Url;
import ar.edu.itba.paw.model.database.UrlDAO;
import ar.edu.itba.paw.service.UrlService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlServiceImpl implements UrlService {
	
	UrlDAO urlDAO;
	
	@Autowired
	public UrlServiceImpl (UrlDAO urlDAO){
		this.urlDAO = urlDAO;
	}
	

	public String shorten(String url) {
		if (Strings.isNullOrEmpty(url)) {
			throw new IllegalArgumentException("Invalid URL");
		}
		String newurl = "/s/";
		Url reverse = urlDAO.findByResol(url);
		if (reverse != null) {
			newurl = newurl.concat(reverse.getBase());
		} else {
			String base = UUID.randomUUID().toString().substring(0, 5);
			newurl = newurl.concat(base);
			urlDAO.add(new Url(base, url));
		}
		return newurl;
	}
	
	public String resolve(String data){
		return urlDAO.findbyBase(data).getResol();
		
	}
	
}
