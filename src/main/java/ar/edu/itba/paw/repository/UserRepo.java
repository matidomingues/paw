package ar.edu.itba.paw.repository;

import java.util.List;

import ar.edu.itba.paw.entity.User;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

public interface UserRepo {
	
	public User authenticate(String username, String password);

	public boolean registerUser(User user) throws DuplicatedUserException;

	public User getUserByUsername(String username);

	public boolean updateUser(User user);

	public List<User> getAll();

	public List<User> find(String username);

	public void userRestore(String username, String secretAnswer,
			String newPassword);

	public User find(int id);
}
