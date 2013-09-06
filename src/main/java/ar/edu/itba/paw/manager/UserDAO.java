package ar.edu.itba.paw.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;

import ar.edu.itba.paw.model.User;


public class UserDAO {
	
	private static UserDAO instance = null;
	HashSet<User> users = new HashSet<User>();
	HashMap<UUID, User> session = new HashMap<UUID, User>();
	
	private UserDAO(){
		
	}
	
	public static synchronized UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	public User getUserByUsername(String username){
		for(User elem: users){
			if(elem.getUsername().compareTo(username) == 0){
				return elem;
			}
		}
		return null;
	}
	
	public UUID authenticate(String username, String password){
		User user = getUserByUsername(username);
		if(user != null){
			if(user.getPassword().compareTo(password) == 0){
				UUID uuid = UUID.randomUUID();
				session.put(uuid, user);
				return uuid;
			}
		}
		return null;
	}
	
	public boolean registerUser(String username, String password, String name, String surname, String description){
		//Si al meterlo en la base de datos no hay problemas ok
		users.add(new User((int)(Math.random()*1000), username, password, name, surname, description, true, DateTime.now()));
		return true;
	}
	
	public User getUserBySession(UUID uuid){
		return session.get(uuid);
	}
	
	public Set<User> find(String username){
		HashSet<User> filteredusers = new HashSet<User>();
		for(User user: users){
			if(user.getUsername().contains(username)){
				filteredusers.add(user);
			}
		}
		return filteredusers;
	}
	
	public Set<User> getAll(){
		return users;
	}
}
