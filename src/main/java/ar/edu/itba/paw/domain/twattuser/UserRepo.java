package ar.edu.itba.paw.domain.twattuser;

import java.util.List;

import ar.edu.itba.paw.domain.twattuser.TwattUser;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

public interface UserRepo {
	
	public TwattUser authenticate(String username, String password);

	public boolean registerUser(TwattUser user) throws DuplicatedUserException;

	public TwattUser getUserByUsername(String username);

	public boolean updateUser(TwattUser user);

	public List<TwattUser> getAll();

	public List<TwattUser> find(String username);

	public void userRestore(String username, String secretAnswer,
			String newPassword);

	public TwattUser find(int id);
}
