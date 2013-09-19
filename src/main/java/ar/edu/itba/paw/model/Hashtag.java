package ar.edu.itba.paw.model;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 05/09/13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public class Hashtag extends PersistableEntity{

    private Twatt firstTweet;
    private String tagName;

    public Hashtag(Twatt firstTweet, String tagName) {
        this(-1, firstTweet, tagName);
    }

    public Hashtag(Integer id, Twatt firstTweet, String tagName) {
        super(id);
        this.firstTweet = firstTweet;
        this.tagName = tagName;

    }

    public Twatt getFirstTweet() {
        return this.firstTweet;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hashtag)) return false;

        Hashtag hashtag = (Hashtag) o;

        if (!firstTweet.equals(hashtag.firstTweet)) return false;
        if (!tagName.equals(hashtag.tagName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstTweet.hashCode();
        result = 31 * result + tagName.hashCode();
        return result;
    }
}
