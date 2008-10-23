package org.lopatka.idonc.web.page.detachablemodel;

import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;

public class DetachableIdoncUserModel extends LoadableDetachableModel {

	private static final long serialVersionUID = 4900056953111927614L;

	private final long id;
	
	private final UserDao userDao;
	
	public DetachableIdoncUserModel(IdoncUser user, UserDao dao) {
		super(user);
		this.id = user.getId();
		this.userDao = dao;
	}
	
	@Override
	protected IdoncUser load() {
		return userDao.load(id);
	}

}
