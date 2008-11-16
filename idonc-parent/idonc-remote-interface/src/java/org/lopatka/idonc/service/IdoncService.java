package org.lopatka.idonc.service;

import java.util.List;

import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;

public interface IdoncService {

	public List<IdoncUser> getUserList(String username, String sessionId);

	public IdoncUser getUserDetails(String usernme, String sessionId, String usernameDetails);

	public List<String> getUserNameList(String username, String sessionId);

	public void updateUser(String username, String sessionId, IdoncUser user);

	public LoggedUser loginUser(String username, String password);

	public boolean registerUser(IdoncUser user, String password);

	public void logoutUser(String userName, String sessionId);
	
	public List getUsers(int first, int count);
	
	public int countUsers();
	
	public IdoncUser loadUser(long id);

	public IdoncProject loadProject(long id);

	public List getProjects(int first, int count);

	public int countProjects();

	public void addProject(IdoncProject project);
}
