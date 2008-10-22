package org.lopatka.idonc.web.service;

import java.io.Serializable;
import java.util.List;

import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;

public class IdoncServiceImpl implements IdoncService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<IdoncUser> getUserList(String username, String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String loginUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public void logoutUser(String username, String sessionId) {

	}

	public String registerUser(IdoncUser user) {
		//userDao.
		// TODO Auto-generated method stub
		return null;
	}

	public void updateUser(IdoncUser user, String username, String sessionId) {
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




}
