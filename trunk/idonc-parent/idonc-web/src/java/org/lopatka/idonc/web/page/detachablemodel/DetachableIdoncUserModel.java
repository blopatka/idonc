package org.lopatka.idonc.web.page.detachablemodel;

import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;

public class DetachableIdoncUserModel extends LoadableDetachableModel {

	private static final long serialVersionUID = 4900056953111927614L;

	private final long id;
	
	private final IdoncService service;
	
	public DetachableIdoncUserModel(IdoncUser user, IdoncService service) {
		super(user);
		this.id = user.getId();
		this.service = service;
	}
	
	@Override
	protected IdoncUser load() {
		return service.loadUser(id);
	}

}
