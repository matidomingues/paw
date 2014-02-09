package ar.edu.itba.paw.helper;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.domain.url.Url;

import java.util.List;

public interface MessageHelper {

    public List<String> prepareMessage(String message);

    public List<Hashtag> getHashtags(String message);

    public List<TwattUser> getMentions(String message);
        
    public List<Url> getUrls(String message);

}