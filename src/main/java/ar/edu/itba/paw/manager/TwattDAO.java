package ar.edu.itba.paw.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ar.edu.itba.paw.model.Twatt;
import ar.edu.itba.paw.model.User;

public class TwattDAO {

	private static TwattDAO instance = null;

	private TwattDAO() {

	}

	public static TwattDAO getInstance() {
		if (instance == null) {
			instance = new TwattDAO();
		}
		return instance;
	}

	public boolean addTwatt(User user, String message) {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO tweet(user_id, message, deleted) values(?, ?, ?)");
			stmt.setInt(1, user.getId());
			stmt.setString(2, message);
			stmt.setBoolean(3, false);
			int result = stmt.executeUpdate();
			connection.commit();
			connection.close();
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	public List<Twatt> getTwattsByUser(User user) {
		List<Twatt> twatts = new LinkedList<Twatt>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, message, created_time, deleted FROM tweet WHERE user_id=? ORDER BY created_time desc");
			stmt.setInt(1, user.getId());
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt(1);
				String message = results.getString(2);
				DateTime created_time = new DateTime(results.getTimestamp(3)
						.getTime());
				Boolean deleted = results.getBoolean(4);
				twatts.add(new Twatt(id, user, message, deleted, created_time));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return twatts;
	}

}
