package ar.edu.itba.paw.domain.twattuser;


import ar.edu.itba.paw.domain.twatt.Twatt;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

import java.util.List;

public interface UserRepo {

    public static final String MENTION_REGEX = "(?:\\s|\\A|^)[@]([A-Za-z0-9-_]+)";
	
	public TwattUser authenticate(String username, String password);

	public boolean registerUser(TwattUser user) throws DuplicatedUserException;

	public TwattUser getUserByUsername(String username);

	public List<TwattUser> getAll();

	public List<TwattUser> find(String username);

	public void userRestore(String username, String secretAnswer,
			String newPassword);

	public TwattUser find(int id);
	
	public List<TwattUser> getRecomendations(TwattUser user);

    List<TwattUser> resolveMentions(Twatt twatt);
    
    public List<String> findUsernames(String username);
}
