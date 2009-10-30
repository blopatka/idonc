package org.lopatka.idonc.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.lopatka.idonc.dao.LoggedUserDao;
import org.lopatka.idonc.dao.ProjectDao;
import org.lopatka.idonc.dao.UserCredentialDao;
import org.lopatka.idonc.dao.UserDao;
import org.lopatka.idonc.exception.IdoncAuthorizationException;
import org.lopatka.idonc.exception.IdoncException;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.IdoncResult;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;
import org.lopatka.idonc.model.user.UserCredential;
import org.lopatka.idonc.service.utils.PasswordHasher;

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

	// Services for login and registering
	public LoggedUser loginUser(String username, String password) {
		if ((username != null) && (password != null)) {
			UserCredential cred = userCredentialDao.get(username);
			if (cred != null) {
				byte[] storedHash = PasswordHasher.base64ToByte(cred
						.getPassword());
				byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
				try {
					byte[] inputHash = PasswordHasher.getHash(1000, password,
							storedSalt);
					if (Arrays.equals(inputHash, storedHash)) {
						// add loggedUser to generate sessionId
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
		} else {
			return null;
		}
	}

	public void logoutUser(String userName, String sessionId) {
		LoggedUser lU = loggedUserDao.getLoggedUserBySession(sessionId);
		if (lU != null) {
			loggedUserDao.deleteLoggedUser(lU);
		}
	}

	public boolean registerUser(IdoncUser user, String password) {
		user = userDao.save(user);

		UserCredential cred = new UserCredential();
		cred.setUser(user);
		byte[] salt;
		try {
			salt = PasswordHasher.createSalt();
			byte[] pass = PasswordHasher.getHash(1000, password, salt);
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

	public boolean editUser(IdoncUser user, String password) {
		user = userDao.save(user);

		UserCredential cred = userCredentialDao.get(user.getUserName());
		cred.setUser(user);
		byte[] salt;
		try {
			salt = PasswordHasher.createSalt();
			byte[] pass = PasswordHasher.getHash(1000, password, salt);
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

	public boolean checkUserAuthorized(String username, String sessionId)
			throws IdoncException {
		LoggedUser loggedUser = loggedUserDao.getLoggedUserBySession(sessionId);
		if (loggedUser != null) {
			if (loggedUser.getUser().getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}

	// General purpose services

	public List<IdoncUser> getUserList(String username, String sessionId)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			// TODO Auto-generated method stub
			return null;
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public void updateUser(String username, String sessionId, IdoncUser user)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			// TODO Auto-generated method stub
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncUser getUserDetails(String queriedUsername, String username,
			String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			// TODO Auto-generated method stub
			return null;
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public List<String> getUserNameList(String username, String sessionId)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			// TODO Auto-generated method stub
			return null;
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public int countUsers(String username, String sessionId)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.count();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public List getUsers(String username, String sessionId, int first, int count)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.get(first, count);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncUser loadUser(String username, String sessionId, long id)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.load(id);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public List getProjects(String username, String sessionId, int first,
			int count) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			List<IdoncProject> projects = projectDao.get();
			return projectDao.get();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncProject loadProject(String username, String sessionId, int id)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return projectDao.load(id);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public int countProjects(String username, String sessionId)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return projectDao.count();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncProject getContributedProject(String username, String sessionId)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			LoggedUser lUser = loggedUserDao.getLoggedUserBySession(sessionId);
			return lUser.getUser().getContributedProject();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncUser setContributedProject(String username, IdoncProject project, String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			LoggedUser lUser = loggedUserDao.getLoggedUserBySession(sessionId);
			IdoncUser user = lUser.getUser();
			project.addActiveUser(user);
			IdoncProject project1 = projectDao.save(project);
			user.setContributedProject(project1);
			return user;
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncPart getPartToProcess(String username, String sessionId)
			throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			IdoncUser user = userDao.findByUsername(username);
			IdoncProject project = user.getContributedProject();
			if(project != null) {
				//ma ustawiony projekt
				List<IdoncPart> parts = projectDao.getParts(project);
				Collections.sort(parts);
			} else {
				//nie ma ustawionego projektu
				return null;
			}
			/*
			 * algorytm pobierania czesci do obliczen
			 * 1. sprawdzic do jakiego projektu nalezy klient (jezeli nie nalezy do zadnego, zwrocic null
			 * 2. dla konkretnego projektu pobrac IdoncPart ktory spelnia ponizsze zalozenia
			 *	a) jest najwczesniej dodanym elementem (najnizsze creationTimestamp)
			 *	b) ma mniej niz 2 aktualnie liczacych uzytkownikow
			 *	c) aktualny klient nie jest tym uzytkownikiem ktory w tej chwili liczy
			 *	d) aktualny klient nie jest dodany do black listy
			 *3. zwrocic IdoncPart ktory spelnia powyzsze warunki
			 */

			throw new UnsupportedOperationException("not yet implemented");
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public void returnProcessingResult(String username, String sessionId,
			IdoncResult result) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			/*
			 * algorytm zwrócenia wyniku obliczen
			 * 1. jesli nikt wczesniej nie zwrocil wyniki dla tego parta, to wpisac wynik
			 * 2. jezeli ktos wczesniej liczyl tego parta to
			 *	a) jezeli otrzymany wynik jest rozny od wyniku zapisanego przez innego uzytkownia nalezy
			 *	   usunac wyniki obliczenia innego uzytkownika, do listy zablokowanych uzytkownikow, dodac
			 *	   aktualnego, oraz tego ktory dodal wczesniej wynik (nie da sie stwierdzic ktory z nich dokonal
			 *	   nieprawidlowych obliczen)
			 *  b) jezeli wyniki sie zgadzaja to nalezy dodac wynik obliczen do parta, a nastepnie przesunac
			 *     parta razem z wynikiem do processedParts w IdoncProject (tzn usunac z listy partsToProcess i dodac do
			 *     processedParts)
			 */
			throw new UnsupportedOperationException("not yet implemented");
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

}
