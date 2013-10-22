package ar.edu.itba.paw.domain.repository;

import java.io.Serializable;
import java.util.List;

import ar.edu.itba.paw.domain.entity.PersistentEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public abstract class AbstractHibernateRepo<T extends PersistentEntity> {
	private final SessionFactory sessionFactory;

	public AbstractHibernateRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public T get(Class<T> type, Serializable id) {
		return (T) getSession().get(type, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... params) {
		Session session = getSession();

		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		List<T> list = query.list();
		return list;
	}

	protected org.hibernate.classic.Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Serializable save(T o) {
		return getSession().save(o);
	}

}
