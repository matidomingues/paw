package ar.edu.itba.paw.helper.implementations;

import ar.edu.itba.paw.helper.HashtagHelper;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.database.HashtagDAO;
import ar.edu.itba.paw.model.database.implamentations.HashtagDAOImpl;
import com.google.common.base.Strings;
import org.joda.time.DateTime;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 14/09/13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
public class HashtagHelperImpl implements HashtagHelper{

    private static HashtagHelperImpl instance;
    private HashtagDAO hashtagDAO;
    private Pattern hashtagPattern;

    private HashtagHelperImpl() {
        this.hashtagDAO = HashtagDAOImpl.getInstance();
        this.hashtagPattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
    }

    public static HashtagHelper getInstance() {
        if (instance == null) {
            instance = new HashtagHelperImpl();
        }
        return instance;
    }

    public void create(Hashtag hashtag) {
        this.hashtagDAO.create(hashtag);
    }

    public void resolveHashtags(Twatt twatt) {
        Matcher hashtagMatcher = this.hashtagPattern.matcher(twatt.getMessage());
        while(hashtagMatcher.find()) {
            String hashtagName = hashtagMatcher.group().trim().split("#")[1];
            Hashtag hashtag = null;
            if ((hashtag = this.getHashtag(hashtagName)) == null) {
                hashtag = new Hashtag(twatt, hashtagName);
                this.create(hashtag);
                hashtag = this.getHashtag(hashtag.getTagName());
            }
            this.relate(hashtag, twatt);
        }
    }

    public Hashtag getHashtag(String hashtag) {
        return this.hashtagDAO.find(hashtag);
    }

    public List<Hashtag> getHashtags(Twatt twatt) {
        return this.hashtagDAO.findForTwatt(twatt);
    }

    public List<Hashtag> getTrendingsHashtagsAfter(DateTime dateTime) {
        return this.hashtagDAO.findTrendingHashtagsAfter(dateTime);
    }

    public void relate(Hashtag hashtag, Twatt twatt) {
        this.hashtagDAO.relate(hashtag, twatt);
    }

    public int getMentions(Hashtag hashtag, DateTime filterDate) {
        return this.hashtagDAO.getMentionsAfter(hashtag, filterDate);
    }

    public boolean isValidHashtag(Hashtag hashtag) {
        return !Strings.isNullOrEmpty(hashtag.getTagName()) &&
                hashtag.getFirstTweet() != null;

    }

}
