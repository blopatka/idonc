package org.lopatka.idonc.web.dao;

import java.util.List;
import java.util.UUID;

import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.model.user.LoggedUser;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LoggedUserDaoImpl extends HibernateDaoSupport implements LoggedUserDao{

	public void deleteLoggedUser(LoggedUser loggedUser) {
		logger.info("deleting LoggedUser information");
		getHibernateTemplate().delete(loggedUser);
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	public LoggedUser createLoggedUser(IdoncUser user) {
		LoggedUser lU = new LoggedUser();
		lU.setUser(user);
		String sessionId = UUID.randomUUID().toString();
		lU.setSessionId(sessionId);
		Long id = (Long) getHibernateTemplate().save(lU);
		return (LoggedUser) getHibernateTemplate().get(LoggedUser.class, id);
	}
	
	

}
