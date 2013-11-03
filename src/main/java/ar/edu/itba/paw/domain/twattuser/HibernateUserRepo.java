package ar.edu.itba.paw.domain.twattuser;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo<TwattUser> implements
		UserRepo {
	
	public TwattUser authenticate(String username, String password) {
		if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
			throw new IllegalArgumentException("Invalid Username or Password");
		}
		TwattUser user = this.getUserByUsername(username);
		if (user != null && user.getPassword().compareTo(password) == 0) {
			return user;
		}
		return null;

	}

	public boolean registerUser(TwattUser user) throws DuplicatedUserException {
        if (user == null) {
            throw  new IllegalArgumentException("Null user");
        }
		if (!user.isValidUser()) {
			throw new IllegalArgumentException("Invalid user");
		} else if (existsUsername(user.getUsername())) {
			throw new DuplicatedUserException();
		} else {
			save(user);
			return true;
		}
	}

	public TwattUser getUserByUsername(String username) {
		if (Strings.isNullOrEmpty(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		List<TwattUser> user = super.find("from TwattUser where username = ?", username);
		if(user.isEmpty()){
			System.out.println("empty");
			return null;
		}
		return user.get(0);
	}

	public boolean updateUser(TwattUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Null User");
        }
		if (!user.isValidUser()) {
			throw new IllegalArgumentException("Invalid user");
		}
	    //save(user);
		return true;
	}

	public List<TwattUser> getAll() {
		return super.find("from TwattUser");
	}

	public List<TwattUser> find(String username) {
		if (Strings.isNullOrEmpty(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		return super.find("from TwattUser where username LIKE '%" + username + "%'");

	}

	public void userRestore(String username, String secretAnswer,
			String newPassword) {
		if (Strings.isNullOrEmpty(username)
				|| Strings.isNullOrEmpty(secretAnswer)
				|| Strings.isNullOrEmpty(newPassword)) {
			throw new IllegalArgumentException(
					"Invalid username, secret answer or password detected");
		}
		TwattUser user = getUserByUsername(username);
		if (user != null && user.getSecretAnswer().compareTo(secretAnswer) == 0) {
			user.setPassword(newPassword);
			updateUser(user);
			return;
		} else {
			throw new IllegalArgumentException("Incorrect Answers");
		}
	}

	public TwattUser find(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("Unexpected id");
		}
		return get(TwattUser.class, id);
	}
	
	private boolean existsUsername(String username){
        if (Strings.isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Null or empty username");
        }
		return !find("from TwattUser where username=?", username).isEmpty();
	}
	
	public List<TwattUser> getRecomendationsByUser(TwattUser user) throws NumberFormatException {
        if (user == null) {
            throw new IllegalArgumentException("Null user");
        }
		int deep = 3;
		List<TwattUser> users = getRecomendations(user, deep);
		Collections.shuffle(users);
		return users.subList(0, 2);
	}
	
	private List<TwattUser> getRecomendations(TwattUser user, Integer deep){
        if (user == null) {
            throw new IllegalArgumentException("Null user");
        }
        if (deep < 0 ) {
            throw new IllegalArgumentException("Invalid depth");
        }
		if(deep == 0){
			return user.getFollowings();
		}
		List<TwattUser> followers = new LinkedList<TwattUser>();
		for(TwattUser follower: user.getFollowings()){
			followers.addAll(getRecomendations(follower,deep-1));
		}
		return followers;
	}

}
