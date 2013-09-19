package ar.edu.itba.paw.helper.implementations;

import ar.edu.itba.paw.helper.UserHelper;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.database.UserDAO;
import ar.edu.itba.paw.model.database.implamentations.UserDAOImpl;
import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserHelperImpl implements UserHelper {

    private static UserHelper instance;
    private UserDAO usermanager = UserDAOImpl.getInstance();

    public static UserHelper getInstance() {
        if (instance == null) {
            instance = new UserHelperImpl();
        }
        return instance;
    }
    private UserHelperImpl() {
        this.usermanager = UserDAOImpl.getInstance();
    }

	public User authenticate(String username, String password) {
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Invalid Username or Password");
        }
		User user = usermanager.getUserByUsername(username);
		if (user != null && user.getPassword().compareTo(password) == 0) {
			return user;
		}
		return null;

	}

	public boolean registerUser(User user) {
        if (!this.isValidUser(user)) {
            throw new IllegalArgumentException("Invalid user");
        }
		return usermanager.registerUser(user);
	}

	public User getUserByUsername(String username) {
        if (Strings.isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Invalid username");
        }
		return usermanager.getUserByUsername(username);
	}

	public boolean updateUser(User user){
        if (!this.isValidUser(user)) {
            throw new IllegalArgumentException("Invalid user");
        }
		return usermanager.updateUser(user);
	}

	public List<User> getAll() {
		return usermanager.getAll();
	}

	public List<User> find(String username) {
        if (Strings.isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Invalid username");
        }
		return usermanager.find(username);
	}
	
	public boolean userRestore(String username, String secretAnswer, String newPassword){
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(secretAnswer) || Strings.isNullOrEmpty(newPassword)) {
            throw new IllegalArgumentException("Invalid username, secrec answer or password detected");
        }
		User user = usermanager.getUserByUsername(username);
		if(user != null && user.getSecretAnswer().compareTo(secretAnswer) == 0){
			user.setPassword(newPassword);
			usermanager.updateUser(user);
			return true;
		}
		return false;
	}

    public boolean isValidUser(User user) {
        return !Strings.isNullOrEmpty(user.getUsername()) &&
                !Strings.isNullOrEmpty(user.getPassword()) &&
                !Strings.isNullOrEmpty(user.getName()) &&
                !Strings.isNullOrEmpty(user.getDescription()) &&
                !Strings.isNullOrEmpty(user.getSurname()) &&
                !Strings.isNullOrEmpty(user.getSecretQuestion()) &&
                !Strings.isNullOrEmpty(user.getSecretAnswer()) &&
                user.getDate() != null;
    }

    @Override
    public User find(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Unexpected id");
        }
        return this.usermanager.find(id);
    }
}
