package org.lopatka.idonc.service;

import java.util.List;

import org.lopatka.idonc.exception.IdoncException;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.IdoncResult;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;

public interface IdoncService {

    public LoggedUser loginUser(String username, String password);

    public void logoutUser(String userName, String sessionId);

	public boolean registerUser(IdoncUser user, String password);


    public boolean checkUserAuthorized(String username, String sessionId) throws IdoncException;

	public List<IdoncUser> getUserList(String username, String sessionId) throws IdoncException;

	public IdoncUser getUserDetails(String usernme, String sessionId, String usernameDetails) throws IdoncException;

	public List<String> getUserNameList(String username, String sessionId) throws IdoncException;

	public void updateUser(String username, String sessionId, IdoncUser user) throws IdoncException;
    
	public List getUsers(String username, String sessionId, int first, int count) throws IdoncException;
	
	public int countUsers(String username, String sessionId) throws IdoncException;
	
	public IdoncUser loadUser(String username, String sessionId, long id) throws IdoncException;

	public IdoncProject loadProject(String username, String sessionId, long id) throws IdoncException;

	public List getProjects(String username, String sessionId, int first, int count) throws IdoncException;

	public int countProjects(String username, String sessionId) throws IdoncException;

	//public void addProject(String username, String sessionId, IdoncProject project) throws IdoncException;

    //Services for desktop client

    public IdoncProject getContributedProject(String username, String sessionId) throws IdoncException;

    public IdoncPart getPartToProcess(String username, String sessionId) throws IdoncException;

    public void returnProcessingResult(String username, String sessionId, IdoncResult result) throws IdoncException;
}
