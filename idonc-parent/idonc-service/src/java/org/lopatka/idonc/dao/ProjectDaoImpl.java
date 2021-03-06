package org.lopatka.idonc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.IdoncResult;
import org.lopatka.idonc.model.data.PartType;
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

	@SuppressWarnings("unchecked")
	public List<IdoncProject> get(int first, int count) {
		Query query = getSession().createQuery("from IdoncProject");
		query.setFirstResult(first);
		query.setMaxResults(count);
		return query.list();
	}

	public IdoncProject load(Long id) {
		return (IdoncProject) getSession().get(IdoncProject.class, id);
	}

	public void add(IdoncProject project) {
		getSession().save(project);
	}

	public List<IdoncPart> getParts(IdoncProject project) {
		IdoncProject proj = load(project.getId());
		List<IdoncPart> parts = proj.getParts();
		return initializeParts(parts);
	}

	public IdoncProject save(IdoncProject project) {
		return (IdoncProject) getSession().merge(project);
	}

	private List<IdoncPart> initializeParts(List<IdoncPart> parts) {
		Hibernate.initialize(parts);
		for (IdoncPart part : parts) {
			Hibernate.initialize(part.getLongDataList());
			Hibernate.initialize(part.getUserProcessing());
			Hibernate.initialize(part.getResults());
		}
		return parts;
	}

	private IdoncPart initializePart(IdoncPart part) {
		Hibernate.initialize(part);
		Hibernate.initialize(part.getLongDataList());
		Hibernate.initialize(part.getUserProcessing());
		Hibernate.initialize(part.getResults());
		return part;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IdoncPart getPartWithConfirmation(String username,
			IdoncProject project) {

		// pobieramy zalogowanego uzytkownika
		Query userQuery = getSession()
				.createQuery(
						"select distinct user from IdoncUser user where user.userName = :username");
		userQuery.setParameter("username", username);
		userQuery.setFirstResult(0);
		userQuery.setMaxResults(1);
		IdoncUser user = (IdoncUser) userQuery.list().get(0);

		// pytamy o elementy ktore byly juz raz liczone (ale nie przez nas)
		Query query = getSession()
				.createQuery(
						"select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType) and (parts.results is not empty) and (parts.userProcessing is not null) and (parts.userProcessing.id != :userId) order by parts.id");
		query.setParameter("id", project.getId());
		query.setParameter("partType", PartType.NEW);
		query.setParameter("userId", user.getId());
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<IdoncPart> resultList = query.list();
		if (resultList.isEmpty()) {
			// nie ma elementow ktore byly liczone i potrzebuja potwierdzenia
			// pytamy o nie liczone elementy
			Query query2 = getSession()
					.createQuery(
							"select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType) and (parts.results is empty) order by parts.id");
			query2.setParameter("id", project.getId());
			query2.setParameter("partType", PartType.NEW);
			query2.setFirstResult(0);
			query2.setMaxResults(1);
			IdoncPart part = (IdoncPart) query2.list().get(0);

			part.setUserProcessing(user);
			part.setPartType(PartType.PROCESSING);
			part.setUpdated(System.currentTimeMillis());
			getSession().update(part);

			part = initializePart(part);
			return part;
		} else {
			// sa elementy (1 element) bierzemy go do obliczen
			IdoncPart part = (IdoncPart) resultList.get(0);
			part = initializePart(part);

			part.setPartType(PartType.PROCESSING);
			part.setUpdated(System.currentTimeMillis());
			getSession().update(part);

			return part;
		}
		// return null;
	}

	@Override
	public IdoncPart getPartWithoutConfirmation(IdoncProject project) {
		// pobranie elementu o statusie NEW
		Query query = getSession()
				.createQuery(
						"select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType) order by parts.id");
		query.setParameter("id", project.getId());
		query.setParameter("partType", PartType.NEW);
		query.setFirstResult(0);
		query.setMaxResults(1);
		IdoncPart part = (IdoncPart) query.list().get(0);
		initializePart(part);

		// zerezerwowanie elementu, czyli nadanie mu statusu ONE_PROCESSING
		part.setPartType(PartType.PROCESSING);
		part.setUpdated(System.currentTimeMillis());
		getSession().update(part);

		return part;
	}

	@Override
	public void returnProcessingResultWithConfirmation(String username,
			IdoncPart part, List<IdoncResult> result) {

		// pobrac parta odpowiedniego
		IdoncPart tPart = (IdoncPart) getSession().get(IdoncPart.class,
				part.getId());
		tPart = initializePart(tPart);

		// sprawdzenie czy już jest obliczony wynik
		List<IdoncResult> tResult = tPart.getResults();
		if ((tResult != null) && (!tResult.isEmpty())) {
			// jest juz wynik, nalezy sprawdzic czy sie zgadza z otrzymanym
			if (checkResultsEqual(tResult, result)) {
				// wynik ten sam, mozna potraktowac jako obliczony
				tPart.setUserProcessing(null);
				tPart.setPartType(PartType.COMPLETED);
			} else {
				// wynik inny, trzeba usunac wynik obliczen aby skierowac do
				// ponownego obliczenia
				tPart.setUserProcessing(null);
				tPart.setResults(null);
				tPart.setPartType(PartType.NEW);

				// usuniecie niepotrzebnego wpisu z bazy
				Query delQuery = getSession().createQuery("delete org.lopatka.idonc.model.data.IdoncResult result where (result.parentPart = :parent)");
				delQuery.setParameter("parent", tPart);
				delQuery.executeUpdate();
			}

		} else {
			// jeszcze nie ma obliczonego wyniku, można zapisac otrzymany
			//result.setParent(tPart);
			for(IdoncResult res : result) {
				res.setParentPart(tPart);
			}

			tPart.setResults(result);
			tPart.setPartType(PartType.NEW);

			// sprawdzic czy zpisany uzytkownik to nasz uzytkownik
			// jak nie, to trzeba tam zapisać nas żebyśmy drugi raz nie liczyli
			if (!tPart.getUserProcessing().getUserName().equals(username)) {
				// pobrac uzytkownika do zapisania
				Query userQuery = getSession()
						.createQuery(
								"select distinct user from IdoncUser user where user.userName = :username");
				userQuery.setParameter("username", username);
				userQuery.setFirstResult(0);
				userQuery.setMaxResults(1);
				IdoncUser user = (IdoncUser) userQuery.list().get(0);
				tPart.setUserProcessing(user);
			}
		}

		// zapisanie zmodyfikowanego parta
		tPart.setUpdated(System.currentTimeMillis());
		getSession().update(tPart);

	}

	@Override
	public void returnProcessingResultWithoutConfirmation(IdoncPart part,
			List<IdoncResult> result) {
		// pobranie odpowiedniego parta
		IdoncPart tPart = (IdoncPart) getSession().get(IdoncPart.class,
				part.getId());
		tPart = initializePart(tPart);

		// ustawienie typu, jako przeliczony
		tPart.setPartType(PartType.COMPLETED);

//		FlushMode oldFlush = getSession().getFlushMode();
//		getSession().setFlushMode(FlushMode.ALWAYS);
		for(IdoncResult r : result) {
			r.setParentPart(tPart);
//			getSession().save(r);
		}
//		getSession().setFlushMode(oldFlush);

		// ustawienie wyniku
		tPart.setResults(result);

		// zapisanie wyniku
		tPart.setUpdated(System.currentTimeMillis());
		getSession().update(tPart);
	}

	@Override
	public void resetAbandonedParts() {
		//resetujemy porzucone partsy, ktore nie zostaly przeliczone przez ostatnie 24h
		Long cutTimestamp = System.currentTimeMillis() - (24 * 60 *60 * 1000);

		//przypadek gdy przerwano obliczenia bez potwierdzenia
		String queryString1 = "update org.lopatka.idonc.model.data.IdoncPart part set part.partType = :partType where (part.partType = :searchPartType) and (part.updated < :cutTime) and (part.userProcessing is null) and (part.results is empty)";
		Query query = getSession().createQuery(queryString1);
		query.setParameter("searchPartType", PartType.PROCESSING);
		query.setParameter("partType", PartType.NEW);
		query.setLong("cutTime", cutTimestamp);
		query.executeUpdate();

		//przypadek gdy przerwano obliczenia z potwierdzeniem przy pierwszym liczeniu
		String queryString2 = "update org.lopatka.idonc.model.data.IdoncPart part set part.partType = :partType, part.userProcessing = null where (part.partType = :searchPartType) and (part.updated < :cutTime) and (part.userProcessing is not null) and (part.results is empty)";
		Query query2 = getSession().createQuery(queryString2);
		query2.setParameter("searchPartType", PartType.PROCESSING);
		query2.setParameter("partType", PartType.NEW);
		query2.setLong("cutTime", cutTimestamp);
		query2.executeUpdate();

		//przypadek gdy przerwano obliczenia z potwierdzeniem przy drugim liczeniu
		String queryString3 = "update org.lopatka.idonc.model.data.IdoncPart part set part.partType = :partType where (part.partType = :searchPartType) and (part.updated < :cutTime) and (part.userProcessing is not null) and (part.results is not empty)";
		Query query3 = getSession().createQuery(queryString3);
		query3.setParameter("searchPartType", PartType.PROCESSING);
		query3.setParameter("partType", PartType.NEW);
		query3.setLong("cutTime", cutTimestamp);
		query3.executeUpdate();
	}

	private Boolean checkResultsEqual(List<IdoncResult> left, List<IdoncResult> right) {
		if(left.size() == right.size()) {
			for(int i = 0; i < left.size(); i++) {
				if(! left.get(i).getValue().equals(right.get(i).getValue())) {
					return Boolean.FALSE;
				}
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public List<IdoncPart> getInputData(Long id) {
		String queryString = "select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project left join fetch parts.longDataList where (parts.project.id = :id) and (parts.partType = :partType)";
		Query query = getSession().createQuery(queryString);
		query.setParameter("id", id);
		query.setParameter("partType", PartType.NEW);
		List<IdoncPart> parts = query.list();

		return parts;
	}

	@Override
	public List<IdoncPart> getOutputData(Long id) {
		String queryString = "select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project left join fetch parts.results where (parts.project.id = :id) and (parts.partType = :partType)";
		Query query = getSession().createQuery(queryString);
		query.setParameter("id", id);
		query.setParameter("partType", PartType.COMPLETED);
		List<IdoncPart> parts = query.list();
		return parts;
	}

}
