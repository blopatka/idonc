package org.lopatka.idonc.web.dao;

import org.lopatka.idonc.web.model.user.UserCredential;

public interface UserCredentialDao {

	public UserCredential load(long id);
	
	public UserCredential save(UserCredential credential);
	
	public void delete(UserCredential credential);
	
	public UserCredential get(String username);
}
