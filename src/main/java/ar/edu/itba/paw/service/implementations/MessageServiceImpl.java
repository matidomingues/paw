package ar.edu.itba.paw.service.implementations;

import ar.edu.itba.paw.service.MessageService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageServiceImpl implements MessageService {

	private Pattern urlPattern;
	private Pattern hashtagPattern;

	public MessageServiceImpl() {
		urlPattern = Pattern.compile("/s/[a-z0-9]{5}");
		hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
	}

	public String prepareMessage(String context, String message) {
		Set<String> alreadyReplaced = new HashSet<String>();
		if (Strings.isNullOrEmpty(message) || context == null) {
			throw new IllegalArgumentException("Invalid Message received");
		}
		Matcher urlMatcher = urlPattern.matcher(message);
		while (urlMatcher.find()) {
			String url = urlMatcher.group();
			if (!alreadyReplaced.contains(url)) {
				message = message.replace(url, "<a target=\"_blank\" href="
						+ context + "/" + url + ">" + url + "</a>");
				alreadyReplaced.add(url);
			}
		}
		Matcher hashtagMatcher = hashtagPattern.matcher(message);
		StringBuffer sb = new StringBuffer();
		while (hashtagMatcher.find()) {
			String hashtag = hashtagMatcher.group();
			hashtagMatcher.appendReplacement(sb, "<a href=\"/hashtag/"
					+ hashtag.trim().split("#")[1] + "\">" + hashtag + "</a>");
		}
		hashtagMatcher.appendTail(sb);
		return sb.toString();
	}
}
