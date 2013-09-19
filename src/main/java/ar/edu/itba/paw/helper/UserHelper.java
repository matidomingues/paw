package ar.edu.itba.paw.helper;

import ar.edu.itba.paw.model.User;

import java.util.List;

public interface UserHelper {

	public User authenticate(String username, String password);

	public boolean registerUser(User user);

	public User getUserByUsername(String username);

	public boolean updateUser(User user);

	public List<User> getAll();

	public List<User> find(String username);

	public boolean userRestore(String username, String secretAnswer,
			String newPassword);

	public boolean isValidUser(User user);

    public User find(int id);
}