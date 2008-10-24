package org.lopatka.idonc.web.dao;

import java.util.Iterator;
import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.utils.QueryParam;

public interface UserDao {

//	public List<IdoncUser> getUserList();
//
//	public void saveUser(IdoncUser user);
//
//	public void registerUser(IdoncUser user);
//	
	//new methods - directly from hibernate
	
	public IdoncUser load(long id);
	
	public IdoncUser save(IdoncUser user);
	
	public void delete(long id);
	
	public Iterator find(QueryParam qp, IdoncUser filter);
	
	public int count(IdoncUser filter);
	
	public List<String> getUniqueUsernames();
	
}