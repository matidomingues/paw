package ar.edu.itba.paw.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.manager.UrlDAO;

public class MessageHelper {
	
	public static UrlDAO urlmanager = UrlDAO.getInstance();
	
	public static String prepareMessage(String message){
        String [] parts = message.split("\\s");
        String finalstring = new String();
        for( String item : parts ) try {
            URL url = new URL(item);
            finalstring.concat(urlmanager.shorten(url.toString()) + " ");
        } catch (MalformedURLException e) {
            finalstring.concat(item + " ");
        }
        System.out.println(message);
        System.out.println(finalstring);
        return finalstring;
	}
	
	public static String prepareMessage2(String message){
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
