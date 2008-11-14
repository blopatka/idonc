package org.lopatka.idonc.web.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {


	public int count() {
		Criteria crit = getSession().createCriteria(IdoncUser.class);
		crit.setProjection(Projections.rowCount());
		Integer count = (Integer) crit.uniqueResult();
		return count;
	}

	public void delete(long id) {
		getSession().delete(load(id));
	}

	@SuppressWarnings("unchecked")
	public Iterator<IdoncUser> get(int first, int count) {
		Criteria crit = getSession().createCriteria(IdoncUser.class);
		crit.setMaxResults(count);
		crit.setFirstResult(first);
		return crit.list().iterator();
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
		IdoncUser user = (IdoncUser) criteria.uniqueResult();
		return user;
		
	}

}
