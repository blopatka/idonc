package org.lopatka.idonc.web.page.dataproviders;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.web.page.detachablemodel.DetachableIdoncUserModel;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncSession;

public class UserDataProvider implements IDataProvider {

	private static final long serialVersionUID = 8897947586615266997L;

	private IdoncService service;

    private IdoncSession session = IdoncSession.get();
	
	public UserDataProvider(IdoncService service) {
		this.service = service;
	}
	
	@SuppressWarnings("unchecked")
	public Iterator iterator(int first, int count) {
        String username = session.getLoggedUserName();
        String sessionId = session.getSessionId();
		return service.getUsers(username, sessionId, first, count).iterator();
	}


	public int size() {
        String username = session.getLoggedUserName();
        String sessionId = session.getSessionId();
		return service.countUsers(username, sessionId);
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
