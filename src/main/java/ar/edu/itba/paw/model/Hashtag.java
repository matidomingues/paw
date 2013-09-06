package ar.edu.itba.paw.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 05/09/13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public class Hashtag extends PersistableEntity{

    private Tweet firstTweet;
    private String tagName;
    private String description;
    private List<Tweet> invokers;

    public Hashtag(Integer id, Tweet firstTweet, String tagName, String description, List<Tweet> invokers) {
        super(id);
        this.firstTweet = firstTweet;
        this.tagName = tagName;
        this.description = description;
        this.invokers = invokers;
    }

    public Tweet getFirstTweet() {
        return this.firstTweet;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDescription() {
        return description;
    }

    public List<Tweet> getInvokers() {
        return invokers;
    }
}