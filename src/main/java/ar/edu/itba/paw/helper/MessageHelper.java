package ar.edu.itba.paw.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.manager.UrlDAO;
import ar.edu.itba.paw.model.Url;

public class MessageHelper {
	
	public static UrlDAO urlmanager = UrlDAO.getInstance();
	
	private static String shorten(String url){
		String newurl = "/s/";
		Url reverse = urlmanager.reverseUrl(url);
		if(reverse != null){
			newurl = newurl.concat(reverse.getBase());
		}else{
			String base = UUID.randomUUID().toString().substring(0, 5);
			newurl = newurl.concat(base);
			urlmanager.addRoute(base, url);
		}
		return newurl;
	}
	
	public static String prepareMessage(String message){
		Pattern urlPattern = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");
		Matcher urlMatcher = urlPattern.matcher(message);
		String newurl;
		while(urlMatcher.find()){
			String oldurl = urlMatcher.group();
			newurl = shorten(oldurl);
			message = message.replace(oldurl, "<a href=\""+newurl+"\">"+newurl+"</a>" );
		}
		System.out.println(message);
		return message;
	}
}
