package ar.edu.itba.paw.model.database.implamentations;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.UserDAO;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

	private static UserDAO instance = null;
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
					.prepareStatement("SELECT id, username, password, name, surname, description, created_time, secret_question, secret_answer, photo FROM twat_user WHERE username = ?");
			stmt.setString(1, username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				user = generateUser(results);
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
					.prepareStatement("INSERT INTO twat_user(username, password, name, surname, created_time, description, enabled, secret_question, secret_answer, photo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getSurname());
            stmt.setTimestamp(5, new Timestamp(user.getDate().getMillis()));
			stmt.setString(6, user.getDescription());
			stmt.setBoolean(7, true);
			stmt.setString(8, user.getSecretQuestion());
			stmt.setString(9, user.getSecretAnswer());
//            stmt.setString(10, Base64.encodeBytes(user.getPhoto()));
            stmt.setBytes(10, user.getPhoto());
			int result = stmt.executeUpdate();
            System.out.println("UserDao:"+connection.toString());
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	public List<User> find(String username) {
		try {
            LinkedList<User> filteredusers = new LinkedList<User>();
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id, username, password, name, surname, description, created_time, secret_question, secret_answer, photo FROM twat_user WHERE username LIKE '%"
							+ username + "%' ORDER BY surname, name");
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				filteredusers.add(generateUser(results));
			}
            return filteredusers;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

    public User find(int id) {
        try {
            User user = null;
            Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = connection
                    .prepareStatement("SELECT id, username, password, name, surname, description, created_time, secret_question, secret_answer, photo FROM twat_user WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                user = generateUser(results);
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
					.prepareStatement("UPDATE twat_user SET (password, name, surname, description, enabled, photo) = (?, ?, ?, ?, ?, ?) where id = ?");
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getSurname());
			stmt.setString(4, user.getDescription());
			stmt.setBoolean(5, true);
//            stmt.setString(6, Base64.encodeBytes(user.getPhoto()));
//            int len = (user.getPhoto() == null)? 0 : user.getPhoto().length;
//            stmt.setBinaryStream(6, new ByteArrayInputStream(user.getPhoto()), len);
            stmt.setBytes(6, user.getPhoto());
			stmt.setInt(7, user.getId());
			Integer result = stmt.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

    private User generateUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String rusername = resultSet.getString("username");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String description = resultSet.getString("description");
        DateTime date = new DateTime(resultSet.getTimestamp(7).getTime());
        String secretQuestion = resultSet.getString("secret_question");
        String secretAnswer = resultSet.getString("secret_answer");
        byte[] photo = resultSet.getBytes("photo");
        return new User(id, rusername, password, name,
                surname, description, date, secretQuestion, secretAnswer, photo);
    }
}
