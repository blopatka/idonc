package org.lopatka.idonc.service.utils;

import org.lopatka.idonc.dao.LoggedUserDao;
import org.lopatka.idonc.dao.ProjectDao;
import org.lopatka.idonc.dao.UserCredentialDao;
import org.lopatka.idonc.dao.UserDao;

public class DatabaseCleaner {

	private UserDao userDao;
	private UserCredentialDao userCredentialDao;
	private LoggedUserDao loggedUserDao;
	private ProjectDao projectDao;

	public void cleanDatabase() {
		System.out.println("cleaning");
		projectDao.resetAbandonedParts();
		loggedUserDao.resetAbandonedSessions();
	}

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


}
