package ar.edu.itba.paw.domain.userlist;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.domain.repository.AbstractHibernateRepo;

@Repository
public class HibernateUserList extends AbstractHibernateRepo<UserList> implements UserListRepo{

	@Override
	public void saveList(UserList list) {
		super.save(list);
	}

	@Override
	public void deleteList(UserList list) {
		super.delete(list);
	}

	@Override
	public void updateList(UserList list) {
		super.forceUpdate(list);
	}

	
}
