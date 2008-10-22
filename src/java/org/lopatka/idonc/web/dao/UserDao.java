package org.lopatka.idonc.web.dao;

import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;

public interface UserDao {

	public List<IdoncUser> getUserList();

	public void saveUser(IdoncUser user);

	public void registerUser(IdoncUser user);
}
