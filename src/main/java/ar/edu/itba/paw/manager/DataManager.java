package ar.edu.itba.paw.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import ar.edu.itba.paw.model.User;

public class DataManager {
	
	private static DataManager instance = null;
	HashSet<User> users = new HashSet<User>();
	HashMap<UUID, User> session = new HashMap<UUID, User>();
	
	private DataManager(){
		
	}
	
	public static synchronized DataManager getInstance(){
		if(instance == null){
			instance = new DataManager();
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
		users.add(new User((int)(Math.random()*1000), username, password, name, surname, description));
		return true;
	}
	
	public User getUserBySession(UUID uuid){
		return session.get(uuid);
	}
	
}
