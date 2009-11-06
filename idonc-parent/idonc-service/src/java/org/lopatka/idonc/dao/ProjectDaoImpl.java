package org.lopatka.idonc.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProjectDaoImpl extends HibernateDaoSupport implements ProjectDao {

	public int count() {
		Criteria crit = getSession().createCriteria(IdoncProject.class);
		crit.setProjection(Projections.rowCount());
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Integer result = (Integer) crit.uniqueResult();
		return result;
	}

	//@SuppressWarnings("unchecked")
	public List<IdoncProject> get(int first, int count) {
		Query query = getSession().createQuery("from IdoncProject");
		query.setFirstResult(first);
		query.setMaxResults(count);
		return query.list();
	}

	public IdoncProject load(int id) {
		return (IdoncProject) getSession().get(IdoncProject.class, id);
	}

	public void add(IdoncProject project) {
		getSession().save(project);
	}

	public List<IdoncPart> getParts(IdoncProject project) {
		IdoncProject proj = load(project.getId());
		List<IdoncPart> parts = proj.getPartsToProcess();
		return initializeParts(parts);
	}

	public IdoncProject save(IdoncProject project) {
		return (IdoncProject) getSession().merge(project);
	}

	private List<IdoncPart> initializeParts(List<IdoncPart> parts) {
		Hibernate.initialize(parts);
		for(IdoncPart part : parts) {
			//Hibernate.initialize(part.getLongDataList());
			Hibernate.initialize(part.getLockedUsers());
			Hibernate.initialize(part.getUsersProcessing());
			Hibernate.initialize(part.getResults());
		}
		return parts;
	}


}
