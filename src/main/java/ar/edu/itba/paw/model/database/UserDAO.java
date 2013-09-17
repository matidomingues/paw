package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA. User: facundo Date: 07/09/13 Time: 20:04 To
 * change this template use File | Settings | File Templates.
 */
public interface UserDAO {
	User getUserByUsername(String username);

	boolean registerUser(User user);

	List<User> find(String username);

	List<User> getAll();

	boolean updateUser(User user);
}
