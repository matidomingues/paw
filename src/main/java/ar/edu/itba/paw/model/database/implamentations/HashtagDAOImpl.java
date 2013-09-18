package ar.edu.itba.paw.model.database.implamentations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.database.HashtagDAO;
import ar.edu.itba.paw.model.database.TwattDAO;
import org.joda.time.DateTime;

public class HashtagDAOImpl implements HashtagDAO {
	
	private static HashtagDAO instance = null;
    private TwattDAO twattDAO;

	private HashtagDAOImpl(){
		
	}
	
	public static HashtagDAO getInstance(){
		if(instance == null){
			instance = new HashtagDAOImpl();
		}
		return instance;
	}
	
    public Set<Hashtag> getTrendingHashtags(){
		return new HashSet<Hashtag>();
	}

    public void create(Hashtag hashtag) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO hashtag (first_tweet, tag_name) values (?,?)");
            statement.setInt(1, hashtag.getFirstTweet().getId());
            statement.setString(2, hashtag.getTagName());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Hashtag find(String tagName) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, first_tweet, tag_name FROM hashtag WHERE tag_name = ?");
            statement.setString(1, tagName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Twatt twatt = this.twattDAO.find(resultSet.getInt("first_tweet"));
                int hashtagId = resultSet.getInt("id");
                return new Hashtag(hashtagId, twatt, tagName);
            }
            return null;
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }


    public Hashtag find(int hashtagId) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, first_tweet, tag_name FROM hashtag WHERE id = ?");
            statement.setInt(1, hashtagId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Twatt twatt = this.twattDAO.find(resultSet.getInt("first_tweet"));
                String tagName = resultSet.getString("tag_name");
                return new Hashtag(hashtagId, twatt, tagName);
            }
            return null;
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    public void relate(Hashtag hashtag, Twatt twatt) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO hashtag_tweet (hashtag_id, tweet_id) values (?, ?)");
            statement.setInt(1, hashtag.getId());
            statement.setInt(2, twatt.getId());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    public List<Hashtag> findForTwatt(Twatt twatt) {
        return this.findForTwatt(twatt.getId());
    }

    public List<Hashtag> findForTwatt(int id) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT hashtag_id FROM hashtag_tweet WHERE tweet_id = ?");
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<Hashtag> hashtags = new LinkedList<Hashtag>();

            while(rs.next()) {
                int hashtagId = rs.getInt("hashtag_id");
                Hashtag hashtag = this.find(hashtagId);
                hashtags.add(hashtag);
            }
            return hashtags;
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    public List<Hashtag> findTrendingHashtagsAfter(DateTime dateTime) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}

