package org.lopatka.idonc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.lopatka.idonc.model.user.IdoncUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {


	public int count() {
		Criteria crit = getSession().createCriteria(IdoncUser.class);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		crit.setProjection(Projections.rowCount());
		Integer count = (Integer) crit.uniqueResult();
		return count;
	}

	public void delete(long id) {
		String queryStr = "delete from IdoncUser where id = :id";
		Query query = getSession().createQuery(queryStr);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<IdoncUser> get(int first, int count) {

		Query query = getSession().createQuery("from IdoncUser");
		query.setFirstResult(first);
		query.setMaxResults(count);
		return query.list();
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

	public IdoncUser findByUsername(String username) {
		Criteria criteria = getSession().createCriteria(IdoncUser.class);
		Criterion crit = Restrictions.eq("userName", username);
		criteria.add(crit);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		IdoncUser user = (IdoncUser) criteria.uniqueResult();
		return user;

	}


	@SuppressWarnings("unchecked")
	public List<IdoncUser> getAllUsers() {
		return getSession().createQuery("select distinct u from IdoncUser u").list();
	}

	@Override
	public int countWithoutAdmins() {
		String queryString = "select count(distinct u.userName) from IdoncUser u where (u not in (from IdoncAdmin))";
		Query query = getSession().createQuery(queryString);
		return ((Long) query.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdoncUser> getWithoutAdmins(int first, int count) {
		String queryString = "select distinct u from IdoncUser u where (u not in (from IdoncAdmin))";
		Query query = getSession().createQuery(queryString);
		query.setFirstResult(first);
		query.setMaxResults(count);
		return query.list();
	}

}
