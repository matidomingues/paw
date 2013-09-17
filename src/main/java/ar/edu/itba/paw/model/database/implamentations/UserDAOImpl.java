package ar.edu.itba.paw.model.database.implamentations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

	private UserDAOImpl() {

	}

	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAOImpl();
		}
		return instance;
	}

    public User getUserByUsername(String username) {
		try {
			User user = null;
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, password, name, surname, description, created_time, secret_question, secret_answer FROM twat_user WHERE username = ?");
			stmt.setString(1, username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				int id = results.getInt(1);
				String password = results.getString(2);
				String name = results.getString(3);
				String surname = results.getString(4);
				String description = results.getString(5);
				DateTime date = new DateTime(results.getTimestamp(6).getTime());
				String secretQuestion = results.getString(7);
				String secretAnswer = results.getString(8);
				user = new User(id, username, password, name, surname,
						description, date, secretQuestion, secretAnswer);
			}
			return user;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public boolean registerUser(User user) {
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO twat_user(username, password, name, surname, description, enabled, secret_question, secret_answer) values(?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getSurname());
			stmt.setString(5, user.getDescription());
			stmt.setBoolean(6, true);
			stmt.setString(7, user.getSecretQuestion());
			stmt.setString(8, user.getSecretAnswer());
			int result = stmt.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	public List<User> find(String username) {
		LinkedList<User> filteredusers = new LinkedList<User>();
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, username, password, name, surname, description, created_time, secret_question, secret_answer FROM twat_user WHERE username LIKE '%"
							+ username + "%' ORDER BY surname, name");
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt(1);
				String rusername = results.getString(2);
				String password = results.getString(3);
				String name = results.getString(4);
				String surname = results.getString(5);
				String description = results.getString(6);
				DateTime date = new DateTime(results.getTimestamp(7).getTime());
				String secretQuestion = results.getString(7);
				String secretAnswer = results.getString(8);
				filteredusers.add(new User(id, rusername, password, name,
						surname, description, date, secretQuestion, secretAnswer));
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return filteredusers;
	}

    public User find(int id) {
        try {
            User user = null;
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT username, password, name, surname, description, created_time, secret_question, secret_answer FROM twat_user WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                String username = results.getString(1);
                String password = results.getString(2);
                String name = results.getString(3);
                String surname = results.getString(4);
                String description = results.getString(5);
                DateTime date = new DateTime(results.getTimestamp(6).getTime());
                String secretQuestion = results.getString(7);
                String secretAnswer = results.getString(8);
                user = new User(id, username, password, name, surname,
                        description, date, secretQuestion, secretAnswer);
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }    }

    public List<User> getAll() {
		return find("");
	}

	public boolean updateUser(User user) {
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE twat_user SET (password, name, surname, description, enabled) = (?, ?, ?, ?, ?) where id = ?");
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getSurname());
			stmt.setString(4, user.getDescription());
			stmt.setBoolean(5, true);
			stmt.setInt(6, user.getId());
			Integer result = stmt.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
