package org.lopatka.idonc.web.service;

import java.util.Iterator;
import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.model.user.LoggedUser;
import org.lopatka.idonc.web.utils.QueryParam;

public interface IdoncService {

	public List<IdoncUser> getUserList(String username, String sessionId);

	public IdoncUser getUserDetails(String usernme, String sessionId, String usernameDetails);

	public List<String> getUserNameList(String username, String sessionId);

	public void updateUser(String username, String sessionId, IdoncUser user);

	public LoggedUser loginUser(String username, String password);

	public boolean registerUser(IdoncUser user, String password);

	public void logoutUser(String userName, String sessionId);
	
	public Iterator findUser(QueryParam qp, IdoncUser user);
	
	public int countUsers(IdoncUser filter);
	
	public IdoncUser loadUser(long id);
}
