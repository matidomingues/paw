package ar.edu.itba.paw.domain.twattuser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;

import ar.edu.itba.paw.domain.twatt.Twatt;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import ar.edu.itba.paw.utils.OrderedLinkedList;
import ar.edu.itba.paw.utils.exceptions.DuplicatedUserException;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo<TwattUser>
		implements UserRepo {

    private Pattern mentionPattern = Pattern.compile(MENTION_REGEX);

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
		List<TwattUser> user = super.find("from TwattUser where username = ?",
				username);
		if (user.isEmpty()) {
			System.out.println("empty");
			return null;
		}
		return user.get(0);
	}

	public List<TwattUser> getAll() {
		return super.find("from TwattUser order by name, surname");
	}

	public List<TwattUser> find(String username) {
		if (Strings.isNullOrEmpty(username)) {
			throw new IllegalArgumentException("Invalid username");
		}
		return super.find("from TwattUser where username LIKE '%" + username
				+ "%' order by name, surname");

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
	
	public List<TwattUser> getRecomendations(TwattUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Null user");
        }
		int deep = 3;
		OrderedLinkedList list = new OrderedLinkedList();
		for (TwattUser following : user.getFollowings()) {
			for (TwattUser candidate : following.getFollowings()) {
				if (!user.equals(candidate)
						&& !user.getFollowings().contains(candidate)) {
					list.add(candidate);
				}
			}
		}
		LinkedList<TwattUser> candidates = list.getMoreThan(deep);
		if (candidates.size() >= 3) {
			Collections.shuffle(candidates);
			return candidates.subList(0, 3);
		}
		List<TwattUser> orderedCandidates = getMostFollowedUsers(user);
		orderedCandidates.removeAll(candidates);
		if (orderedCandidates.size() < 3
				&& orderedCandidates.size() >= candidates.size()) {
			candidates.addAll(orderedCandidates.subList(0,
					(orderedCandidates.size() - candidates.size())));
		} else {
			candidates.addAll(orderedCandidates.subList(0,
					(3 - candidates.size())));
		}
		return candidates;
	}

    @Override
    public List<TwattUser> resolveMentions(Twatt twatt) {
        List<TwattUser> twattUsers = new LinkedList<TwattUser>();
        Matcher mentionMatcher = mentionPattern.matcher(twatt.getMessage());
        while(mentionMatcher.find()) {
            String match = mentionMatcher.group();
            List<TwattUser> us = this.find(match.trim().split("@")[1]);
            if (!us.isEmpty()) {
                twattUsers.add(us.get(0));
            }
        }
        return twattUsers;
    }

    private List<TwattUser> getMostFollowedUsers(TwattUser user) {
		Session session = super.getSession();
		String sql;
		if (user.getFollowings().size() != 0) {
			sql = "select size(followers), username from TwattUser u where username != ? and u not in (:ids) group by username";
		} else {
			sql = "select size(followers), username from TwattUser where username != ? group by username";
		}
		Query query = session.createQuery(sql);
		query.setParameter(0, user.getUsername());
		if (user.getFollowings().size() != 0) {
			query.setParameterList("ids", user.getFollowings());
		}
		List<Object[]> list = query.list();
		OrderedLinkedList followed = new OrderedLinkedList();
		for (Object[] data : list) {
			followed.add(getUserByUsername((String) data[1]), (Integer) data[0]);
		}
		return followed.getOrderedByCount();
	}

}
