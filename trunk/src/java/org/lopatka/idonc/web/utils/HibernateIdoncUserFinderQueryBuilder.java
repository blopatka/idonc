package org.lopatka.idonc.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;
import org.lopatka.idonc.web.model.user.IdoncUser;


/**
 * @author Kare Nuorteva
 */
public class HibernateIdoncUserFinderQueryBuilder {
	private List<String> parameters;
	private List<NullableType> types;
	private boolean count;
	private IdoncUser filter = new IdoncUser();
	private QueryParam queryParam;

	public String buildHql() {
		parameters = new ArrayList<String>();
		types = new ArrayList<NullableType>();
		StringBuilder hql = new StringBuilder();
		addCountClause(hql);
		hql.append("from IdoncUser target where 1=1 ");
		addMatchingCondition(hql, filter.getUserName(), "userName");
		addMatchingCondition(hql, filter.getFirstName(), "firstName");
		addMatchingCondition(hql, filter.getLastName(), "lastName");
		addMatchingCondition(hql, filter.getAddress().getCity(), "address.city");
		addMatchingCondition(hql, filter.getAddress().getCountry(), "address.country");
		addMatchingCondition(hql, filter.getAddress().getEmail(), "address.email");
		addMatchingCondition(hql, filter.getAddress().getHouseNumber(), "address.houseNumber");
		addMatchingCondition(hql, filter.getAddress().getStreet(), "address.street");
		addMatchingCondition(hql, filter.getAddress().getWebsite(), "address.website");
		addMatchingCondition(hql, filter.getAddress().getZipCode(), "address.zipCode");
		addOrderByClause(hql);
		return hql.toString();
	}

	private void addCountClause(StringBuilder hql) {
		if (count) {
			hql.append("select count(*) ");
		}
	}

	private void addMatchingCondition(StringBuilder hql, String value, String name) {
		if (value != null) {
			hql.append("and upper(target.");
			hql.append(name);
			hql.append(") like (?)");
			parameters.add("%" + value.toUpperCase() + "%");
			types.add(Hibernate.STRING);
		}
	}

	private void addOrderByClause(StringBuilder hql) {
		if (!count && queryParam != null && queryParam.hasSort()) {
			hql.append("order by upper(target.");
			hql.append(queryParam.getSort());
			hql.append(") ");
			hql.append(queryParam.isSortAsc() ? "asc" : "desc");
		}
	}

	public void setQueryParam(QueryParam queryParam) {
		this.queryParam = queryParam;
	}

	public void setFilter(IdoncUser filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Null value not allowed.");
		}
		this.filter = filter;
	}

	public void setCount(boolean count) {
		this.count = count;
	}

	public String[] getParameters() {
		return parameters.toArray(new String[0]);
	}

	public NullableType[] getTypes() {
		return types.toArray(new NullableType[0]);
	}
}
