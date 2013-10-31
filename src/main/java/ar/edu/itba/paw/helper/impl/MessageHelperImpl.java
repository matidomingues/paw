package ar.edu.itba.paw.helper.impl;

import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.helper.MessageHelper;

import com.google.common.base.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageHelperImpl implements MessageHelper {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UrlRepo urlRepo;

	private final Pattern urlPattern;
	private final Pattern hashtagPattern;
    private final Pattern mentionPattern;

	public MessageHelperImpl() {
		urlPattern = Pattern.compile("/s/[a-z0-9]{5}");
		hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
        mentionPattern = Pattern.compile("(?:\\s|\\A|^)[@]([A-Za-z0-9-_]+)");
	}

	public String prepareMessage(String context, String message) {
		Set<String> alreadyReplaced = new HashSet<String>();
		if (Strings.isNullOrEmpty(message) || context == null) {
			throw new IllegalArgumentException("Invalid Message received");
		}
		Matcher urlMatcher = urlPattern.matcher(message);
        message = this.surround(context, message, new Replacer() {
            @Override
            public String replace(String context, String match) {
                if (Strings.isNullOrEmpty(urlRepo.resolve(match))) {
                    return match;
                }
                return "<a target=\"_blank\" href="
                        + context + "/" + match + ">" + match + "</a>";
            }
        }, urlMatcher);
//		while (urlMatcher.find()) {
//			String url = urlMatcher.group();
//			if (!alreadyReplaced.contains(url)) {
//				message = message.replace(url, "<a target=\"_blank\" href="
//						+ context + "/" + url + ">" + url + "</a>");
//				alreadyReplaced.add(url);
//			}
//		}
		Matcher hashtagMatcher = hashtagPattern.matcher(message);
        message = this.surround(context, message, new Replacer() {
            @Override
            public String replace(String context, String match) {
                return "<a href=\"" + context + "/hashtag/"
                        + match.trim().split("#")[1] + "\">" + match + "</a>";
            }
        }, hashtagMatcher);
        Matcher mentionMatcher = mentionPattern.matcher(message);
        message = this.surround(context, message, new Replacer() {
            @Override
            public String replace(String context, String match) {
                String username = match.trim().split("@")[1];
                if (userRepo.getUserByUsername(username) == null) {
                    return match;
                }
                return "<a href=\"" + context + "/profile/"
                        + username + "\">" + match + "</a>";
            }
        }, mentionMatcher);
		return message;
	}

    private String surround(String context, String original, Replacer replacer, Matcher matcher) {
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            matcher.appendReplacement(sb, replacer.replace(context, match));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private abstract class Replacer {
        public abstract String replace(String context, String match);
    }
}
