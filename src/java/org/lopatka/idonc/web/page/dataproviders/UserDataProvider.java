package org.lopatka.idonc.web.page.dataproviders;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.detachablemodel.DetachableIdoncUserModel;
import org.lopatka.idonc.web.utils.QueryParam;

public class UserDataProvider implements IDataProvider<IdoncUser> {

	private UserDao dao;

	private IdoncUser filter = new IdoncUser();
	
	private QueryParam qp;
	
	public UserDataProvider(UserDao dao) {
		this.dao = dao;
	}
	
	public Iterator<IdoncUser> iterator(int first, int count) {
		if (qp == null) {
			qp = new QueryParam(first, count);
		} else {
			qp.setFirst(first);
			qp.setCount(count);
		}
		return dao.find(qp, filter);
	}

	public IModel<IdoncUser> model(IdoncUser object) {
		return new DetachableIdoncUserModel(object, dao);
	}

	public int size() {
		return dao.count(filter);
	}

	public void detach() {
	}

}
