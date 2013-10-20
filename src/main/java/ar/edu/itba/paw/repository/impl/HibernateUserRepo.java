package ar.edu.itba.paw.repository.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;

import ar.edu.itba.paw.entity.User;
import ar.edu.itba.paw.repository.UserRepo;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

public class HibernateUserRepo extends AbstractHibernateRepo implements
		UserRepo {
	
	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public User authenticate(String username, String password) {
		if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
			throw new IllegalArgumentException("Invalid Username or Password");
		}
		User user = this.getUserByUsername(username);
		if (user != null && user.getPassword().compareTo(password) == 0) {
			return user;
		}
		return null;

	}

	public boolean registerUser(User user) throws DuplicatedUserException {
		if (!user.isValidUser()) {
			throw new IllegalArgumentException("Invalid user");
		} else if (existsUsername(user.getUsername())) {
			throw new DuplicatedUserException();
		} else {
			save(user);
			return true;
		}
	}

	public User getUserByUsername(String username) {
		if (Strings.isNullOrEmpty(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		return (User)(find("from User where username=?", username).get(0));
	}

	public boolean updateUser(User user) {
		if (!user.isValidUser()) {
			throw new IllegalArgumentException("Invalid user");
		}
		save(user);
		return true;
	}

	public List<User> getAll() {
		return find("from User");
	}

	public List<User> find(String username) {
		if (Strings.isNullOrEmpty(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		return find("from User where username LIKE%?%", username);

	}

	public void userRestore(String username, String secretAnswer,
			String newPassword) {
		if (Strings.isNullOrEmpty(username)
				|| Strings.isNullOrEmpty(secretAnswer)
				|| Strings.isNullOrEmpty(newPassword)) {
			throw new IllegalArgumentException(
					"Invalid username, secrec answer or password detected");
		}
		User user = getUserByUsername(username);
		if (user != null && user.getSecretAnswer().compareTo(secretAnswer) == 0) {
			user.setPassword(newPassword);
			updateUser(user);
			return;
		} else {
			throw new IllegalArgumentException("Incorrect Answers");
		}
	}

	public User find(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("Unexpected id");
		}
		return get(User.class, id);
	}
	
	private boolean existsUsername(String username){
		return !find("from User where username=?", username).isEmpty();
	}

}
