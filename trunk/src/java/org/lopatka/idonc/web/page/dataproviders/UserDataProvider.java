package org.lopatka.idonc.web.page.dataproviders;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.detachablemodel.DetachableIdoncUserModel;
import org.lopatka.idonc.web.service.IdoncService;

public class UserDataProvider implements IDataProvider {

	private static final long serialVersionUID = 8897947586615266997L;

	private IdoncService service;
	
	public UserDataProvider(IdoncService service) {
		this.service = service;
	}
	
	@SuppressWarnings("unchecked")
	public Iterator iterator(int first, int count) {
		return service.getUsers(first, count);
	}


	public int size() {
		return service.countUsers();
	}

	public void detach() {
	}

	public IModel model(Object object) {
		if (object instanceof IdoncUser) {
			IdoncUser user = (IdoncUser) object;
			return new DetachableIdoncUserModel(user, service);
		}
		throw new IllegalArgumentException("object shoul be IdoncUser type");
	}


}
