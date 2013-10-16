package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.User;

import java.util.List;

public interface UserDAO {

	User getUserByUsername(String username);

	boolean registerUser(User user);

	List<User> find(String username);

    User find(int id);

	List<User> getAll();

	boolean updateUser(User user);
}
