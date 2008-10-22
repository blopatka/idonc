package org.lopatka.idonc.web.dao;

import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.model.user.LoggedUser;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LoggedUserDaoImpl extends HibernateDaoSupport implements LoggedUserDao{

	public void deleteLoggedUser(LoggedUser loggedUser) {
		logger.info("deletin LoggedUser information");
		getHibernateTemplate().delete(loggedUser);
	}

	public boolean deleteLoggedUserById(Long id) {
		logger.info("deletin LoggedUser information");
		HibernateTemplate template = getHibernateTemplate();
		LoggedUser toDelete = new LoggedUser();
		toDelete.setId(id);
		List<LoggedUser> returned = template.findByExample(toDelete);
		if (returned.size() == 1) {
			toDelete = returned.get(0);
			template.delete(toDelete);
			return true;
		}else {
			return false;
		}

	}

	public LoggedUser getLoggedUserBySession(String sessionId) {
		LoggedUser example = new LoggedUser();
		example.setSessionId(sessionId);
		List<LoggedUser> ret = getHibernateTemplate().findByExample(example);
		if (ret.size() == 1) {
			return ret.get(0);
		} else {
			return null;
		}
	}

	public LoggedUser getLoggedUserByUser(IdoncUser user) {
		LoggedUser example = new LoggedUser();
		example.setUser(user);
		List<LoggedUser> ret = getHibernateTemplate().findByExample(example);
		if(ret.size() == 1) {
			return ret.get(0);
		} else {
			return null;
		}
	}

}
