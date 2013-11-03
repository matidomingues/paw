package ar.edu.itba.paw.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ar.edu.itba.paw.domain.twattuser.TwattUser;

public class OrderedLinkedList {
	TreeMap<Integer, InnerList> list = new TreeMap<Integer, InnerList>(
			new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					return o1.compareTo(o2);
				}

			});
	Map<TwattUser, Integer> userList = new HashMap<TwattUser, Integer>();

	public void add(TwattUser user, Integer pos) {
		if (userList.containsKey(user)) {
			if (userList.get(user).compareTo(pos) != 0) {
				list.get(userList.get(user)).removeUser(user);
				userList.put(user, pos);
				if (list.containsKey(pos)) {
					list.get(pos).addUser(user);
				} else {
					list.put(pos, new InnerList(user));
				}
			}
		} else {
			userList.put(user, pos);
			if (list.containsKey(pos)) {
				list.get(pos).addUser(user);
			} else {
				list.put(pos, new InnerList(user));
			}

		}
	}

	public void add(TwattUser user) {
		if (userList.containsKey(user)) {
			Integer num = userList.get(user);
			list.get(num).removeUser(user);
			if (list.containsKey(num + 1)) {
				list.get(num + 1).addUser(user);
			} else {
				list.put(num + 1, new InnerList(user));
			}
			userList.put(user, num + 1);
		} else {
			userList.put(user, 1);
			if (list.containsKey(1)) {
				list.get(1).addUser(user);
			} else {
				list.put(1, new InnerList(user));
			}
		}
	}

	public LinkedList<TwattUser> getMoreThan(Integer num) {
		LinkedList<TwattUser> candidates = new LinkedList<TwattUser>();
		for (Integer key : list.keySet()) {
			if (key >= num) {
				candidates.addAll(list.get(key).list);
			}
		}
		return candidates;
	}

	public LinkedList<TwattUser> getOrderedByCount() {
		LinkedList<TwattUser> candidates = new LinkedList<TwattUser>();
		for (Integer key : list.descendingKeySet()) {
			candidates.addAll(list.get(key).list);
		}
		return candidates;
	}

	private class InnerList {
		Set<TwattUser> list;

		public InnerList(TwattUser user) {
			this.list = new HashSet<TwattUser>();
			this.list.add(user);
		}

		public void addUser(TwattUser user) {
			this.list.add(user);
		}

		public void removeUser(TwattUser user) {
			this.list.remove(user);
		}
	}
}
