package org.lopatka.idonc.web.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lopatka.idonc.web.dao.LoggedUserDao;
import org.lopatka.idonc.web.dao.ProjectDao;
import org.lopatka.idonc.web.dao.UserCredentialDao;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.data.IdoncProject;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.model.user.LoggedUser;
import org.lopatka.idonc.web.model.user.UserCredential;
import org.lopatka.idonc.web.utils.PasswordHasher;

public class IdoncServiceImpl implements IdoncService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private UserDao userDao;
	private UserCredentialDao userCredentialDao;
	private LoggedUserDao loggedUserDao;
	private ProjectDao projectDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserCredentialDao getUserCredentialDao() {
		return userCredentialDao;
	}
	
	public void setUserCredentialDao(UserCredentialDao userCredentialDao) {
		this.userCredentialDao = userCredentialDao;
	}
	
	public LoggedUserDao getLoggedUserDao() {
		return loggedUserDao;
	}
	
	public void setLoggedUserDao(LoggedUserDao loggedUserDao) {
		this.loggedUserDao = loggedUserDao;
	}
	
	public ProjectDao getProjectDao() {
		return projectDao;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	

	public List<IdoncUser> getUserList(String username, String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public LoggedUser loginUser(String username, String password) {
		if((username != null) && (password != null)) {
			UserCredential cred = userCredentialDao.get(username);
			byte[] storedHash = PasswordHasher.base64ToByte(cred.getPassword());
			byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
			try {
				byte[] inputHash = PasswordHasher.getHash(1000, password, storedSalt);
				if(Arrays.equals(inputHash, storedHash)) {
					//add loggedUser to generate sessionId
					IdoncUser user = userDao.findByUsername(username);
					return loggedUserDao.createLoggedUser(user);
				} else {
					return null;
				}
			} catch (NoSuchAlgorithmException e) {
				return null;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public boolean registerUser(IdoncUser user, String password) {
		user = userDao.save(user);
		
		UserCredential cred = new UserCredential();
		cred.setUser(user);
		byte[] salt;
		try {
			salt = PasswordHasher.createSalt();
			byte[] pass = PasswordHasher.getHash(1000,password , salt);
			cred.setSalt(PasswordHasher.byteToBase64(salt));
			cred.setPassword(PasswordHasher.byteToBase64(pass));
			userCredentialDao.save(cred);
		} catch (NoSuchAlgorithmException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		
		return true;
	}

	public void updateUser(String username, String sessionId, IdoncUser user) {
		// TODO Auto-generated method stub

	}

	public IdoncUser getUserDetails(String queriedUsername, String username,
			String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getUserNameList(String username, String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void logoutUser(String userName, String sessionId) {
		LoggedUser lU = loggedUserDao.getLoggedUserBySession(sessionId);
		if (lU != null) {
			loggedUserDao.deleteLoggedUser(lU);
		}
	}

	public int countUsers() {
		return userDao.count();
	}

	public Iterator getUsers(int first, int count) {
		return userDao.get(first, count);
	}

	public IdoncUser loadUser(long id) {
		return userDao.load(id);
	}

	public Iterator getProjects(int first, int count) {
		return projectDao.get(first, count);
	}

	public IdoncProject loadProject(long id) {
		return projectDao.load(id);
	}
	
	public int countProjects() {
		return projectDao.count();
	}

	public void addProject(IdoncProject project) {
		projectDao.add(project);
	}
	
}
