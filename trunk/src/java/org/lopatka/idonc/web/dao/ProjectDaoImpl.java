package org.lopatka.idonc.web.dao;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.lopatka.idonc.web.model.data.IdoncProject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProjectDaoImpl extends HibernateDaoSupport implements ProjectDao {

	public int count() {
		Criteria crit = getSession().createCriteria(IdoncProject.class);
		crit.setProjection(Projections.rowCount());
		Integer result = (Integer) crit.uniqueResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	public Iterator get(int first, int count) {
		Criteria  crit = getSession().createCriteria(IdoncProject.class);
		crit.setMaxResults(count);
		crit.setFirstResult(first);
		return crit.list().iterator();
	}

	public IdoncProject load(long id) {
		return (IdoncProject) getSession().get(IdoncProject.class, new Long(id));
	}

	public void add(IdoncProject project) {
		getSession().save(project);
	}

}
