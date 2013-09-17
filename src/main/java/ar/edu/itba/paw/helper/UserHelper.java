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

	public User authenticate(String username, String password) {
		User user = usermanager.getUserByUsername(username);
		if (user != null && user.getPassword().compareTo(password) == 0) {
			return user;
		}
		return null;

	}

	public boolean registerUser(User user) {
		return usermanager.registerUser(user);
	}

	public User getUserByUsername(String username) {
		return usermanager.getUserByUsername(username);
	}

	public boolean updateUser(User user){
		return usermanager.updateUser(user);
	}

	public List<User> getAll() {
		return usermanager.getAll();
	}

	public List<User> find(String username) {
		return usermanager.find(username);
	}
	
	public boolean userRestore(String username, String secretAnswer, String newPassword){
		User user = usermanager.getUserByUsername(username);
		if(user != null && user.getSecretAnswer().compareTo(secretAnswer) == 0){
			user.setPassword(newPassword);
			usermanager.updateUser(user);
			return true;
		}
		return false;
	}
}
