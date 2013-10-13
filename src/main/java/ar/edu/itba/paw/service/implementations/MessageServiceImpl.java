package ar.edu.itba.paw.service.implementations;

import ar.edu.itba.paw.model.Url;
import ar.edu.itba.paw.model.database.UrlDAO;
import ar.edu.itba.paw.model.database.implementations.UrlDAOImpl;
import ar.edu.itba.paw.service.MessageService;

import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    public UrlDAO urlmanager;
    private Pattern urlPattern;
    private Pattern hashtagPattern;

    @Autowired
    public MessageServiceImpl(UrlDAO urlDAO) {
        urlmanager = urlDAO;
        urlPattern = Pattern
                .compile("/s/[a-z0-9]{5}");
        hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
    }

	public String shorten(String url) {
        if (Strings.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("Invalid URL");
        }
		String newurl = "/s/";
		Url reverse = urlmanager.reverseUrl(url);
		if (reverse != null) {
			newurl = newurl.concat(reverse.getBase());
		} else {
			String base = UUID.randomUUID().toString().substring(0, 5);
			newurl = newurl.concat(base);
			urlmanager.addRoute(base, url);
		}
		return newurl;
	}

    public String prepareMessage(String context, String message) {
        Set<String> alreadyReplaced = new HashSet<String>();
    	if (Strings.isNullOrEmpty(message) || context == null) {
            throw new IllegalArgumentException("Invalid Message received");
        }
        Matcher urlMatcher = urlPattern.matcher(message);
		while (urlMatcher.find()) {
			String url = urlMatcher.group();
			if(!alreadyReplaced.contains(url)){
				message = message.replace(url, "<a target=\"_blank\" href="+ context +"/" + url + ">"
						+ url + "</a>");
				alreadyReplaced.add(url);
			}
		}
        Matcher hashtagMatcher = hashtagPattern.matcher(message);
        while (hashtagMatcher.find()) {
            String hashtag = hashtagMatcher.group();
            message = message.replace(hashtag, "<a href=\"/hashtag/"+hashtag.trim().split("#")[1]+"\">"+hashtag+"</a>");
        }
		return message;
	}
}