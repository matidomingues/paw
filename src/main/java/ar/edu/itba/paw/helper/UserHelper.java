package ar.edu.itba.paw.helper;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import ar.edu.itba.paw.manager.UserDAO;
import ar.edu.itba.paw.model.User;

public class UserHelper {

	UserDAO usermanager = UserDAO.getInstance();
	HashMap<UUID, User> session = new HashMap<UUID, User>();
	
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
			String surname, String description) {
		usermanager.registerUser(username, password, name, surname, description);
		User user = usermanager.getUserByUsername(username);
		if(user != null){
			UUID uuid = UUID.randomUUID();
			session.put(uuid, user);
			return uuid;
		}
		return null;
	}
	
	public User getUserByUsername(String username){
		return usermanager.getUserByUsername(username);
	}
	
	public boolean updateUserByUUID(UUID uuid, String name, String surname, String password, String description){
		User user = session.get(uuid);
		if(user != null){
			user.setName(name);
			user.setSurname(surname);
			user.setPassword(password);
			user.setDescription(description);
			usermanager.updateUser(user);
			return true;
		}else{
			return false;
		}
	}
	
	public Set<User> getAll(){
		return usermanager.getAll();
	}
	
	public Set<User> find(String username){
		return usermanager.find(username);
	}
}
