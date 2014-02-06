package ar.edu.itba.paw.helper.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.hashtag.HashtagRepo;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.twattuser.UserRepo;
import ar.edu.itba.paw.domain.url.Url;
import ar.edu.itba.paw.domain.url.UrlRepo;
import ar.edu.itba.paw.helper.MessageHelper;

import com.google.common.base.Strings;

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
		urlPattern = Pattern.compile(UrlRepo.SHORT_URL_REGEX);
		hashtagPattern = Pattern.compile(HashtagRepo.HASHTAG_REGEX);
        mentionPattern = Pattern.compile(UserRepo.MENTION_REGEX);
	}

	public String prepareMessage(String message) {
		if (Strings.isNullOrEmpty(message)) {
			throw new IllegalArgumentException("Invalid Message received");
		}
		Matcher urlMatcher = urlPattern.matcher(message);
        message = this.surround(message, new Replacer() {
        	@Override
            public String replace(String match) {
                String data = match.trim().split("/")[2];
                if (Strings.isNullOrEmpty(urlRepo.resolve(data))) {
                    return match;
                }
                return "<a wicket:id=\"url\">" + match + "</a>";
            }
        }, urlMatcher);
		Matcher hashtagMatcher = hashtagPattern.matcher(message);
        message = this.surround(message, new Replacer() {
            @Override
            public String replace(String match) {
                return "<a wicket:id=\"hashtag\">" + match + "</a>";
            }
        }, hashtagMatcher);
        Matcher mentionMatcher = mentionPattern.matcher(message);
        message = this.surround(message, new Replacer() {
            @Override
            public String replace(String match) {
                String username = match.trim().split("@")[1];
                if (userRepo.getUserByUsername(username) == null) {
                    return match;
                }
                return "<a wicket:id=\"mention\">" + match + "</a>";
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
    
    @Override
    public List<Url> getUrls(String message) {
    	List<Url> urls = new LinkedList<Url>();
    	Matcher urlMatcher = urlPattern.matcher(message);
    	while(urlMatcher.find()) {
    		String match = urlMatcher.group();
    		urls.add(urlRepo.find(match.trim().split("/")[2]));
    	}
    	return urls;
    }

    private String surround(String original, Replacer replacer, Matcher matcher) {
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            matcher.appendReplacement(sb, replacer.replace(match));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private abstract class Replacer {
        public abstract String replace(String match);
    }

}
