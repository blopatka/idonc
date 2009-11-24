package org.lopatka.idonc.dao;

import java.util.List;

import org.lopatka.idonc.model.user.IdoncUser;

public interface UserDao {

	public IdoncUser load(long id);

	public IdoncUser save(IdoncUser user);

	public void delete(long id);

	public List<IdoncUser> get(int first, int count);

	public IdoncUser findByUsername(String username);

	public int count();

	public List<String> getUniqueUsernames();

	public List<IdoncUser> getAllUsers();

	public int countWithoutAdmins();

	public List<IdoncUser> getWithoutAdmins(int first, int count);

}
