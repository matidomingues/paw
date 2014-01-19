package ar.edu.itba.paw.domain.twattuser;


import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

import java.util.List;

public interface UserRepo {
	
	public TwattUser authenticate(String username, String password);

	public boolean registerUser(TwattUser user) throws DuplicatedUserException;

	public TwattUser getUserByUsername(String username);

	public List<TwattUser> getAll();

	public List<TwattUser> find(String username);

	public void userRestore(String username, String secretAnswer,
			String newPassword);

	public TwattUser find(int id);
	
	public List<TwattUser> getRecomendations(TwattUser user);
}
