package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

import java.util.List;

public interface UserService {

	public User authenticate(String username, String password);

	public boolean registerUser(User user) throws DuplicatedUserException;

	public User getUserByUsername(String username);

	public boolean updateUser(User user);

	public List<User> getAll();

	public List<User> find(String username);

	public void userRestore(String username, String secretAnswer,
			String newPassword);

	public boolean isValidUser(User user);

    public User find(int id);
}