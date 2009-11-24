package org.lopatka.idonc.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AdminDaoImpl extends HibernateDaoSupport implements AdminDao {

	@Override
	public boolean isAdminUser(String username) {
		logger.debug("checking if user is admin");
		String queryString = "select admin from IdoncAdmin admin where (admin.user.userName = :username)";
		Query query = getSession().createQuery(queryString);
		query.setParameter("username", username);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List ret = query.list();
		if(ret.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
