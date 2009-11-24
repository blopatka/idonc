package org.lopatka.idonc.dao;

import org.lopatka.idonc.model.user.UserCredential;

public interface UserCredentialDao {

	public UserCredential load(long id);

	public UserCredential save(UserCredential credential);

	public void delete(UserCredential credential);

	public UserCredential get(String username);

	public void deleteByIdoncUserId(Long id);
}
