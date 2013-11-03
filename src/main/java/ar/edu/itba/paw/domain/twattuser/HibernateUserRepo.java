package ar.edu.itba.paw.domain.twattuser;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo<TwattUser> implements
		UserRepo {
	
	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

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
					"Invalid username, secrec answer or password detected");
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
		return !find("from TwattUser where username=?", username).isEmpty();
	}

}
