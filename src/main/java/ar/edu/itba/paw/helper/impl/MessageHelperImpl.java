package ar.edu.itba.paw.helper.impl;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.helper.MessageHelper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageHelperImpl implements MessageHelper {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UrlRepo urlRepo;

    @Autowired
    private HashtagRepo hashtagRepo;

	private final Pattern urlPattern;
	private final Pattern hashtagPattern;
    private final Pattern mentionPattern;

	public MessageHelperImpl() {
		urlPattern = Pattern.compile(urlRepo.SHORT_URL_REGEX);
		hashtagPattern = Pattern.compile(hashtagRepo.HASHTAG_REGEX);
        mentionPattern = Pattern.compile(userRepo.MENTION_REGEX);
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
                String data = match.trim().split("/")[2];
                if (Strings.isNullOrEmpty(urlRepo.resolve(data))) {
                    return match;
                }
                return "<a target=\"_blank\" href="
                        + context + "/bin" + match + ">" + match + "</a>";
            }
        }, urlMatcher);
		Matcher hashtagMatcher = hashtagPattern.matcher(message);
        message = this.surround(context, message, new Replacer() {
            @Override
            public String replace(String context, String match) {
                return "<a href=\"" + context + "/bin/hashtag/"
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
                return "<a href=\"" + context + "/bin/profile/"
                        + username + "\">" + match + "</a>";
            }
        }, mentionMatcher);
		return message;
	}

    @Override
    public List<Hashtag> getHashtags(String message) {
        List<Hashtag> hashtags = new LinkedList<Hashtag>();
        Matcher hashtagMatcher = hashtagPattern.matcher(message);
        while(hashtagMatcher.find()) {
            String match = hashtagMatcher.group();
            hashtags.add(hashtagRepo.getHashtag(match.trim().split("#")[1]));
        }
        return hashtags;
    }

    @Override
    public List<TwattUser> getMentions(String message) {
        List<TwattUser> twattUsers = new LinkedList<TwattUser>();
        Matcher mentionMatcher = mentionPattern.matcher(message);
        while(mentionMatcher.find()) {
            String match = mentionMatcher.group();
            List<TwattUser> us = userRepo.find(match.trim().split("@")[1]);
            if (!us.isEmpty()) {
                twattUsers.add(us.get(0));
            }
        }
        return twattUsers;
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
