package ar.edu.itba.paw.model.database.implamentations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.database.UserDAO;
import org.joda.time.DateTime;

import ar.edu.itba.paw.model.User;

public class UserDAOImpl implements UserDAO {

	private static UserDAO instance = null;

	HashSet<User> users = new HashSet<User>();
	HashMap<UUID, User> session = new HashMap<UUID, User>();

	private UserDAOImpl() {

	}

	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAOImpl();
		}
		return instance;
	}

	@Override
    public User getUserByUsername(String username) {
		try {
			User user = null;
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, password, name, surname, description, created_time FROM twat_user WHERE username = ?");
			stmt.setString(1, username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				int id = results.getInt(1);
				String password = results.getString(2);
				String name = results.getString(3);
				String surname = results.getString(4);
				String description = results.getString(5);
				DateTime date = new DateTime(results.getDate(6).getTime());
				user = new User(id, username, password, name, surname,
						description, date);
			}
			connection.close();
			return user;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	@Override
    public UUID authenticate(String username, String password) {
		User user = getUserByUsername(username);
		if (user != null && user.getPassword().compareTo(password) == 0) {
			UUID uuid = UUID.randomUUID();
			session.put(uuid, user);
			return uuid;
		}
		return null;

	}

	@Override
    public boolean registerUser(String username, String password, String name,
                                String surname, String description) {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO twat_user(username, password, name, surname, description, enabled) values(?, ?, ?, ?, ?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setString(4, surname);
			stmt.setString(5, description);
			stmt.setBoolean(6, true);
			int result = stmt.executeUpdate();
			connection.commit();
			connection.close();
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	@Override
    public User getUserBySession(UUID uuid) {
		return session.get(uuid);
	}

	@Override
    public Set<User> find(String username) {
		HashSet<User> filteredusers = new HashSet<User>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, username, password, name, surname, description, created_time FROM twat_user WHERE username LIKE '%"
							+ username + "%' ORDER BY surname, name");
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt(1);
				String rusername = results.getString(2);
				String password = results.getString(3);
				String name = results.getString(4);
				String surname = results.getString(5);
				String description = results.getString(6);
				DateTime date = new DateTime(results.getDate(7).getTime());
				filteredusers.add(new User(id, rusername, password, name,
						surname, description, date));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return filteredusers;
	}

	@Override
    public Set<User> getAll() {
		return find("");
	}

	@Override
    public void updateUser(User user) {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE twat_user SET (password, name, surname, description, enabled) = (?, ?, ?, ?, ?) where id = ?");
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getSurname());
			stmt.setString(4, user.getDescription());
			stmt.setBoolean(5, true);
			stmt.setInt(6, user.getId());
			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
