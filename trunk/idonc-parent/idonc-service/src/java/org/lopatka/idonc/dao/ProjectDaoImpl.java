package org.lopatka.idonc.dao;


import java.util.List;

import org.hibernate.Criteria;
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
		for(IdoncPart part : parts) {
			Hibernate.initialize(part.getLongDataList());
			//Hibernate.initialize(part.getLockedUsers());
			Hibernate.initialize(part.getUserProcessing());
			Hibernate.initialize(part.getResult());
		}
		return parts;
	}

	private IdoncPart initializePart(IdoncPart part) {
		Hibernate.initialize(part);
		Hibernate.initialize(part.getLongDataList());
		//Hibernate.initialize(part.getLockedUsers());
		Hibernate.initialize(part.getUserProcessing());
		Hibernate.initialize(part.getResult());
		return part;
	}

	@Override
	public IdoncPart getPartWithConfirmation(String username, IdoncProject project) {
		/* TODO
		* 2. dla konkretnego projektu pobrac IdoncPart ktory spelnia ponizsze zalozenia
		 *	a) jest najwczesniej dodanym elementem (najnizsze creationTimestamp)
		 *	b) ma mniej niz 2 aktualnie liczacych uzytkownikow
		 *	c) aktualny klient nie jest tym uzytkownikiem ktory w tej chwili liczy
		 *	d) aktualny klient nie jest dodany do black listy
		 */

		//FIXME - poprawic query do pobierania parta do obliczen - w tej chwili
		//nie uwzglednia faktu ze juz obliczal ten fragment (userprocessing) i
		//sciaga tego samego parta 2 razy pod rzad

		//pobieramy zalogowanego uzytkownika
		Query userQuery = getSession().createQuery("select distinct user from IdoncUser user where user.userName = :username");
		userQuery.setParameter("username", username);
		userQuery.setFirstResult(0);
		userQuery.setMaxResults(1);
		IdoncUser user = (IdoncUser) userQuery.list().get(0);

		//pytamy o elementy ktore byly juz raz liczone (ale nie przez nas)
		Query query = getSession().createQuery("select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType) and (parts.result is not null) and (parts.userProcessing is not null) and (parts.userProcessing.id != :userId) order by parts.id");
		query.setParameter("id", project.getId());
		query.setParameter("partType", PartType.ONE_PROCESSING);
		query.setParameter("userId", user.getId());
		query.setFirstResult(0);
		query.setMaxResults(1);
		List resultList = query.list();
		if(resultList.isEmpty()) {
			//nie ma elementow ktore byly liczone i potrzebuja potwierdzenia
			//pytamy o nie liczone elementy
			Query query2 = getSession().createQuery("select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and order by parts.id");
			query2.setParameter("id", project.getId());
			query2.setParameter("partType", PartType.NEW);
			query2.setFirstResult(0);
			query2.setMaxResults(1);
			IdoncPart part = (IdoncPart) query2.list().get(0);

			part.setUserProcessing(user);
			part.setPartType(PartType.ONE_PROCESSING);
			getSession().update(part);

			part = initializePart(part);
			return part;
		} else {
			//sa elementy (1 element) bierzemy go do obliczen
			IdoncPart part = (IdoncPart) resultList.get(0);
			part = initializePart(part);

			part.setPartType(PartType.TWO_PROCESSING);
			getSession().update(part);


			return part;
		}
//		return null;
	}

	@Override
	public IdoncPart getPartWithoutConfirmation(IdoncProject project) {
		//pobranie elementu o statusie NEW
		Query query = getSession().createQuery("select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType) order by parts.id");
		query.setParameter("id", project.getId());
		query.setParameter("partType", PartType.NEW);
		query.setFirstResult(0);
		query.setMaxResults(1);
		IdoncPart part = (IdoncPart) query.list().get(0);
		initializePart(part);

		//zerezerwowanie elementu, czyli nadanie mu statusu ONE_PROCESSING
		part.setPartType(PartType.ONE_PROCESSING);
		getSession().update(part);

		return part;
	}

	@Override
	public void returnProcessingResultWithConfirmation(String username, IdoncPart part, IdoncResult result) {
		// TODO

		// pobrac parta odpowiedniego
		IdoncPart tPart = (IdoncPart) getSession().get(IdoncPart.class, part.getId());
		tPart = initializePart(tPart);

		//sprawdzenie czy już jest obliczony wynik
		IdoncResult tResult = tPart.getResult();
		if(tResult != null) {
			//jest juz wynik, nalezy sprawdzic czy sie zgadza z otrzymanym
			if(tResult.getValue().equals(result.getValue())) {
				//wynik ten sam, mozna potraktowac jako obliczony
				tPart.setUserProcessing(null);
				tPart.setPartType(PartType.PROCESSED);
			} else {
				//wynik inny, trzeba usunac wynik obliczen aby skierowac do ponownego obliczenia
				tPart.setUserProcessing(null);
				tPart.setResult(null);
				tPart.setPartType(PartType.NEW);

				//usuniecie niepotrzebnego wpisu z bazy
				getSession().delete(tResult);
			}

		} else {
			//jeszcze nie ma obliczonego wyniku, można zapisac otrzymany
			result.setParent(tPart);
			Long id = (Long) getSession().save(result);
			IdoncResult t2Result = (IdoncResult) getSession().get(IdoncResult.class, id);
			tPart.setResult(t2Result);

			//pobrac uzytkownika do zapisania
			Query userQuery = getSession().createQuery("select distinct user from IdoncUser user where user.userName = :username");
			userQuery.setParameter("username", username);
			userQuery.setFirstResult(0);
			userQuery.setMaxResults(1);
			IdoncUser user = (IdoncUser) userQuery.list().get(0);
			tPart.setUserProcessing(user);
		}

		//zapisanie zmodyfikowanego parta
		getSession().update(tPart);



	}

	@Override
	public void returnProcessingResultWithoutConfirmation(IdoncPart part,
			IdoncResult result) {
		//pobranie odpowiedniego parta
		IdoncPart tPart = (IdoncPart) getSession().get(IdoncPart.class, part.getId());
		tPart = initializePart(tPart);

		//ustawienie typu, jako przeliczony
		tPart.setPartType(PartType.PROCESSED);

		result.setParent(tPart);
		Long id = (Long) getSession().save(result);
		IdoncResult tResult = (IdoncResult) getSession().get(IdoncResult.class, id);

		//ustawienie wyniku
		tPart.setResult(tResult);

		//zapisanie wyniku
		getSession().update(tPart);
	}



}
