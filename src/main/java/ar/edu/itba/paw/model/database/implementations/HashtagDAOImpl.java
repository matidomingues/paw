package ar.edu.itba.paw.model.database.implementations;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.database.HashtagDAO;
import ar.edu.itba.paw.model.database.TwattDAO;
import ar.edu.itba.paw.utils.exceptions.InvalidHashtagException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class HashtagDAOImpl implements HashtagDAO {
	
    private TwattDAO twattDAO;

    @Autowired
	public HashtagDAOImpl(TwattDAO twattDAO){
		this.twattDAO = twattDAO;
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
                return generateHashtag(resultSet);
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
                return generateHashtag(resultSet);
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
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT h.id, h.first_tweet, h.tag_name, count(*) AS tweet_count " +
                            "FROM hashtag h " +
                            "INNER JOIN hashtag_tweet ht ON ht.hashtag_id = h.id " +
                            "INNER JOIN tweet t ON t.id = ht.tweet_id " +
                            "WHERE t.created_time >= ?" +
                            "GROUP BY h.id, h.first_tweet, h.tag_name " +
                            "ORDER BY tweet_count DESC");
            statement.setTimestamp(1, new Timestamp(dateTime.getMillis()));
            ResultSet resultSet = statement.executeQuery();
            return generateHashtags(resultSet);
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    public int getMentionsAfter(Hashtag hashtag, DateTime filterDate) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT count(*) " +
                            "FROM tweet t " +
                            "INNER JOIN hashtag_tweet ht ON ht.tweet_id = t.id " +
                            "WHERE ht.hashtag_id = ? AND t.created_time >= ?");
            statement.setInt(1, hashtag.getId());
            statement.setTimestamp(2, new Timestamp(filterDate.getMillis()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            throw new InvalidHashtagException();
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    private List<Hashtag> generateHashtags(ResultSet resultSet) throws SQLException {
        List<Hashtag> hashtags = new LinkedList<Hashtag>();
        while(resultSet.next()) {
            hashtags.add(generateHashtag(resultSet));
        }
        return hashtags;
    }

    private Hashtag generateHashtag(ResultSet resultSet) throws SQLException {
        int hashtagId = resultSet.getInt("id");
        Twatt twatt = this.twattDAO.find(resultSet.getInt("first_tweet"));
        String tagName = resultSet.getString("tag_name");
        return new Hashtag(hashtagId, twatt, tagName);
    }
}

