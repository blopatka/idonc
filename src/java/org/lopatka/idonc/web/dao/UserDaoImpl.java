package org.lopatka.idonc.web.dao;

import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public List<IdoncUser> getUserList() {
		logger.info("Getting users list");
		return getHibernateTemplate().find("from org.lopatka.idonc.model.user.IdoncUser");
	}

	public void saveUser(IdoncUser user) {
		logger.info("Saving user: " + user.getUserName());
		getHibernateTemplate().saveOrUpdate(user);
	}

	public void registerUser(IdoncUser user) {
		logger.info("Registering user: " + user.getUserName());
		//getHibernateTemplate()
	}

}
