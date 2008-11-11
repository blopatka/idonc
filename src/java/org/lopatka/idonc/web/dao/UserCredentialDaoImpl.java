package org.lopatka.idonc.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.model.user.UserCredential;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserCredentialDaoImpl extends HibernateDaoSupport implements UserCredentialDao {
	
	public void delete(UserCredential credential) {
		getSession().delete(credential);
	}

	@SuppressWarnings("unchecked")
	public UserCredential get(String username) {
		Criteria usrCrit = getSession().createCriteria(IdoncUser.class);
		Criterion crit = Restrictions.eq("userName", username);
		usrCrit.add(crit);
		List userList = usrCrit.list();
		IdoncUser user = null;
		if(userList.size() == 1) {
			user = (IdoncUser) userList.get(0);
			Criteria credCrit = getSession().createCriteria(UserCredential.class);
			Criterion crit1 = Restrictions.eq("user", user);
			credCrit.add(crit1);
			List credList = credCrit.list();
			if(credList.size() == 1) {
				return (UserCredential) credList.get(0);
			}
		}
		return null;
	}

	public UserCredential load(long id) {
		return (UserCredential) getSession().get(IdoncUser.class, id);
	}

	public UserCredential save(UserCredential credential) {
		return (UserCredential) getSession().merge(credential);
	}

}
