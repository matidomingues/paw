package ar.edu.itba.paw.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.manager.UrlDAO;

public class MessageHelper {
	
	public static UrlDAO urlmanager = UrlDAO.getInstance();
	
	public static String prepareMessage(String message){
		Pattern urlPattern = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");
		Matcher urlMatcher = urlPattern.matcher(message);
		String newurl;
		while(urlMatcher.find()){
			String oldurl = urlMatcher.group();
			newurl = urlmanager.shorten(oldurl);
			message = message.replace(oldurl, "<a href=\""+newurl+"\">"+newurl+"</a>" );
		}
		System.out.println(message);
		return message;
	}
}
