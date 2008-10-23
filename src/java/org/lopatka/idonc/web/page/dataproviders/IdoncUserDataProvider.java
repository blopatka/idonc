package org.lopatka.idonc.web.page.dataproviders;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.detachablemodel.DetachableIdoncUserModel;
import org.lopatka.idonc.web.utils.QueryParam;

public class IdoncUserDataProvider extends SortableDataProvider implements IFilterStateLocator {

	private static final long serialVersionUID = -2114056413799112584L;

	private final UserDao userDao;
	
	private IdoncUser filter = new IdoncUser();
	
	private QueryParam queryParam;
	
	public IdoncUserDataProvider(UserDao dao) {
		this.userDao = dao;
		setSort("userName", true);
	}
	
	public void setQueryParam(QueryParam qp) {
		this.queryParam = qp;
	}
	
	public Iterator iterator(int first, int count) {
		SortParam sp = getSort();
		
		if(queryParam == null) {
			queryParam = new QueryParam(first, count, sp.getProperty(), sp.isAscending());
		} else {
			queryParam.setFirst(first);
			queryParam.setCount(count);
			queryParam.setSort(sp.getProperty());
			queryParam.setSortAsc(sp.isAscending());
		}
		
		return userDao.find(queryParam, filter);
	}

	public IModel model(Object object) {
		if (object instanceof IdoncUser) {
			IdoncUser user = (IdoncUser) object;
			return new DetachableIdoncUserModel(user, userDao);
		}
		throw new IllegalArgumentException("object shoul be IdoncUser type");
	}

	public int size() {
		return userDao.count(filter);
	}

	public Object getFilterState() {
		return filter;
	}

	public void setFilterState(Object state) {
		this.filter = (IdoncUser) state;
	}

}
