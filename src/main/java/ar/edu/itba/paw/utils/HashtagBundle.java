package ar.edu.itba.paw.utils;

import ar.edu.itba.paw.model.Hashtag;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 18/09/13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class HashtagBundle {

    private Hashtag hashtag;
    private int mentions;

    public HashtagBundle(Hashtag hashtag, int mentions) {
        this.hashtag = hashtag;
        this.mentions = mentions;
    }

    public Hashtag getHashtag() {
        return hashtag;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }
}
