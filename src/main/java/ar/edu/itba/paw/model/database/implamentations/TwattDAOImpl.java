package ar.edu.itba.paw.model.database.implamentations;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.Hashtag;
import ar.edu.itba.paw.model.database.TwattDAO;
import ar.edu.itba.paw.model.database.UserDAO;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

public class TwattDAOImpl implements TwattDAO {

	private static TwattDAO instance = null;
    private UserDAO  userDAO;

	private TwattDAOImpl(){
        this.userDAO = UserDAOImpl.getInstance();
	}
	
	public static TwattDAO getInstance(){
		if(instance == null){
			instance = new TwattDAOImpl();
		}
		return instance;
	}

	public boolean create(Twatt twatt){
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO tweet(user_id, message, created_time, deleted) values(?, ?, ?, ?)");
			stmt.setInt(1, twatt.getCreator().getId());
			stmt.setString(2, twatt.getMessage());
            stmt.setTimestamp(3, new Timestamp(twatt.getTimestamp().getMillis()));
			stmt.setBoolean(4, false);
			int result = stmt.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

    public void update(Twatt twatt) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE tweet SET user_id=?, message=?, created_time=?, deleted=? WHERE id=?");
            statement.setInt(1, twatt.getCreator().getId());
            statement.setString(2,twatt.getMessage());
            statement.setTimestamp(3, new Timestamp(twatt.getTimestamp().getMillis()));
            statement.setBoolean(4, twatt.isDeleted());
            statement.setInt(5, twatt.getId());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

	public List<Twatt> find(User user) {
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, user_id, message, created_time, deleted FROM tweet WHERE user_id=? ORDER BY created_time desc");
			stmt.setInt(1, user.getId());
			ResultSet results = stmt.executeQuery();
            return generateTwatts(results);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

    public Twatt find(int id) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT id, user_id, message, created_time, deleted FROM tweet WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery();
            results.next();
            return generateTwatt(results);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    public Twatt find(Twatt twatt) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, user_id, message, created_time, deleted FROM tweet WHERE message = ? AND created_time = ? AND user_id = ?");
            statement.setString(1, twatt.getMessage());
            statement.setTimestamp(2, new Timestamp(twatt.getTimestamp().getMillis()));
            statement.setInt(3, twatt.getCreator().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return generateTwatt(resultSet);
            }
            return null;
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    public List<Twatt> find(Hashtag hashtag) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT t.id, t.user_id, t.message, t.created_time, t.deleted " +
                            "FROM tweet t " +
                            "LEFT JOIN hashtag_tweet ht ON ht.tweet_id = t.id " +
                            "WHERE ht.hashtag_id = ?");
            statement.setInt(1, hashtag.getId());
            ResultSet results = statement.executeQuery();
            return generateTwatts(results);
        } catch (SQLException sqle) {
            throw new DatabaseException(sqle.getMessage(), sqle);
        }
    }

    private Twatt generateTwatt(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        User user = this.userDAO.find(resultSet.getInt("user_id"));
        String message = resultSet.getString("message");
        DateTime createdTime = new DateTime(resultSet.getTimestamp("created_time").getTime());
        boolean deleted = resultSet.getBoolean("deleted");
        return new Twatt(id, user, message,deleted,createdTime);
    }

    private List<Twatt> generateTwatts(ResultSet results) throws SQLException {
        List<Twatt> twatts = new LinkedList<Twatt>();
        while(results.next()) {
            twatts.add(generateTwatt(results));
        }
        return twatts;
    }
}
