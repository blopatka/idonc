package org.lopatka.idonc.web.dao;

import java.util.Iterator;
import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;

public interface UserDao {
	
	public IdoncUser load(long id);
	
	public IdoncUser save(IdoncUser user);
	
	public void delete(long id);
	
	@SuppressWarnings("unchecked")
	public Iterator get(int first, int count);
	
	public IdoncUser findByUsername(String username);
	
	public int count();
	
	public List<String> getUniqueUsernames();
	
}
