package org.lopatka.idonc.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;
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
		Criteria loggCrit = getSession().createCriteria(LoggedUser.class);
		Criterion crit = Restrictions.eq("id", id);
		loggCrit.add(crit);
		loggCrit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<LoggedUser> ret = loggCrit.list();
		if (ret.size() == 1) {
			LoggedUser toDelete = ret.get(0);
			template.delete(toDelete);
			return true;
		}else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public LoggedUser getLoggedUserBySession(String sessionId) {
		Criteria loggCrit = getSession().createCriteria(LoggedUser.class);
		Criterion crit = Restrictions.eq("sessionId", sessionId);
		loggCrit.add(crit);
		loggCrit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<LoggedUser> ret = loggCrit.list();
		if (ret.size() == 1) {
			return ret.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public LoggedUser getLoggedUserByUser(IdoncUser user) {
		Criteria loggCrit = getSession().createCriteria(LoggedUser.class);
		Criterion crit = Restrictions.eq("user", user);
		loggCrit.add(crit);
		loggCrit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<LoggedUser> ret = loggCrit.list();
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
