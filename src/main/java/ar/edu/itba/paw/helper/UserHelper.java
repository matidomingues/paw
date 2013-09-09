package ar.edu.itba.paw.helper;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.UserDAO;
import ar.edu.itba.paw.model.database.implamentations.UserDAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserHelper {

	private UserDAO usermanager = UserDAOImpl.getInstance();
	private static HashMap<UUID, User> session = new HashMap<UUID, User>();

	public UUID authenticate(String username, String password) {
		User user = usermanager.getUserByUsername(username);
		if (user != null && user.getPassword().compareTo(password) == 0) {
			UUID uuid = UUID.randomUUID();
			session.put(uuid, user);
			return uuid;
		}
		return null;

	}

	public User getUserBySession(UUID uuid) {
		return session.get(uuid);
	}

	public UUID registerUser(String username, String password, String name,
			String surname, String description, String secretQuestion,
			String secretAnswer) {
		usermanager.registerUser(username, password, name, surname,
				description, secretQuestion, secretAnswer);
		User user = usermanager.getUserByUsername(username);
		if (user != null) {
			UUID uuid = UUID.randomUUID();
			session.put(uuid, user);
			return uuid;
		}
		return null;
	}

	public User getUserByUsername(String username) {
		return usermanager.getUserByUsername(username);
	}

	public boolean updateUserByUUID(UUID uuid, String name, String surname,
			String password, String description) {
		User user = session.get(uuid);
		if (user != null) {
			user.setName(name);
			user.setSurname(surname);
			user.setPassword(password);
			user.setDescription(description);
			usermanager.updateUser(user);
			return true;
		} else {
			return false;
		}
	}

	public List<User> getAll() {
		return usermanager.getAll();
	}

	public List<User> find(String username) {
		return usermanager.find(username);
	}

	public void logout(UUID uuid) {
		session.remove(uuid);
	}
}
