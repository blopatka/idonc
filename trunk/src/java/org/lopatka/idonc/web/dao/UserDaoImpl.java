package org.lopatka.idonc.web.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.utils.HibernateIdoncUserFinderQueryBuilder;
import org.lopatka.idonc.web.utils.QueryParam;

public class UserDaoImpl implements UserDao {

	private SessionFactory factory;

	public void setSessionFactory(SessionFactory factory) {
		this.factory = factory;
	}

	protected Session getSession() {
		return factory.getCurrentSession();
	}

//	public List<IdoncUser> getUserList() {
//		// logger.info("Getting users list");
//		// return getHibernateTemplate().find(
//		// "from org.lopatka.idonc.model.user.IdoncUser");
//		throw new UnsupportedOperationException("not implemented");
//	}
//
//	public void saveUser(IdoncUser user) {
//		// logger.info("Saving user: " + user.getUserName());
//		// getHibernateTemplate().saveOrUpdate(user);
//		throw new UnsupportedOperationException("not implemented");
//	}
//
//	public void registerUser(IdoncUser user) {
//		// logger.info("Registering user: " + user.getUserName());
//		// //getHibernateTemplate()
//		throw new UnsupportedOperationException("not implemented");
//	}

	public int count(IdoncUser filter) {
		return ((Long) buildFindQuery(null, filter, true).uniqueResult())
				.intValue();
	}

	public void delete(long id) {
		getSession().delete(load(id));
	}

	@SuppressWarnings("unchecked")
	public Iterator<IdoncUser> find(QueryParam qp, IdoncUser filter) {
		return buildFindQuery(qp, filter, false).list().iterator();
	}

	@SuppressWarnings("unchecked")
	public List<String> getUniqueUsernames() {
		return getSession()
				.createQuery(
						"select distinct target.userName from IdoncUser target order by target.userName")
				.list();
	}

	public IdoncUser load(long id) {
		return (IdoncUser) getSession().get(IdoncUser.class, new Long(id));
	}

	public IdoncUser save(IdoncUser user) {
		return (IdoncUser) getSession().merge(user);
	}

	protected Query buildFindQuery(QueryParam qp, IdoncUser filter,
			boolean count) {
		HibernateIdoncUserFinderQueryBuilder builder = new HibernateIdoncUserFinderQueryBuilder();
		builder.setQueryParam(qp);
		builder.setFilter(filter);
		builder.setCount(count);
		Query query = getSession().createQuery(builder.buildHql());
		query.setParameters(builder.getParameters(), builder.getTypes());
		if (!count && qp != null) {
			query.setFirstResult(qp.getFirst()).setMaxResults(qp.getCount());
		}
		return query;
	}

	public IdoncUser findByUsername(String username) {
		Criteria criteria = getSession().createCriteria(IdoncUser.class);
		Criterion crit = Restrictions.eq("userName", username);
		criteria.add(crit);
		IdoncUser user = (IdoncUser) criteria.uniqueResult();
		return user;
		
	}

}
