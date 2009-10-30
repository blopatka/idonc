package org.lopatka.idonc.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Projections;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProjectDaoImpl extends HibernateDaoSupport implements ProjectDao {

	public int count() {
		Criteria crit = getSession().createCriteria(IdoncProject.class);
		crit.setProjection(Projections.rowCount());
		Integer result = (Integer) crit.uniqueResult();
		return result;
	}

	//@SuppressWarnings("unchecked")
	public List<IdoncProject> get() {
		Criteria  crit = getSession().createCriteria(IdoncProject.class);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return crit.list();
	}

	public IdoncProject load(int id) {
		return (IdoncProject) getSession().get(IdoncProject.class, id);
	}

	public void add(IdoncProject project) {
		getSession().save(project);
	}

	public List<IdoncPart> getParts(IdoncProject project) {
		IdoncProject proj = load(project.getId());
		return proj.getPartsToProcess();
	}

	public IdoncProject save(IdoncProject project) {
		return (IdoncProject) getSession().merge(project);
	}



}
