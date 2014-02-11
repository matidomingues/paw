package ar.edu.itba.paw.domain.userlist;

public interface UserListRepo {

	public void saveList(UserList list);
	public void deleteList(UserList list);
	public void updateList(UserList list);
}
