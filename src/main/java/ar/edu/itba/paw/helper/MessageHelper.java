package ar.edu.itba.paw.helper;

import ar.edu.itba.paw.domain.hashtag.Hashtag;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

import java.util.List;

public interface MessageHelper {

    public String prepareMessage(String context, String message);

    public List<Hashtag> getHashtags(String message);

    public List<TwattUser> getMentions(String message);

}