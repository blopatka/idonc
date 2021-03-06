package org.lopatka.idonc.service;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.lopatka.idonc.dao.AdminDao;
import org.lopatka.idonc.dao.LoggedUserDao;
import org.lopatka.idonc.dao.ProjectDao;
import org.lopatka.idonc.dao.UserCredentialDao;
import org.lopatka.idonc.dao.UserDao;
import org.lopatka.idonc.exception.IdoncAuthorizationException;
import org.lopatka.idonc.exception.IdoncException;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.IdoncResult;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;
import org.lopatka.idonc.model.user.UserCredential;
import org.lopatka.idonc.model.util.PasswordHasher;

public class IdoncServiceImpl implements IdoncService, Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private UserCredentialDao userCredentialDao;
	private LoggedUserDao loggedUserDao;
	private ProjectDao projectDao;
	private AdminDao adminDao;

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

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	// Services for login and registering
	public LoggedUser loginUser(String username, String password) {
		if ((username != null) && (password != null)) {
			UserCredential cred = userCredentialDao.get(username);
			if (cred != null) {
				byte[] storedHash = PasswordHasher.base64ToByte(cred.getPassword());
				byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
				try {
					byte[] inputHash = PasswordHasher.getHash(1000, password, storedSalt);
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

	@Override
	public boolean checkUserExists(String username) {
		IdoncUser returned = userDao.findByUsername(username);
		if (returned != null) {
			return false;
		} else {
			return true;
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

	@Override
	public boolean changePassword(IdoncUser user, String oldPassword, String newPassword) {
		UserCredential cred = userCredentialDao.get(user.getUserName());
		if (cred != null) {
			byte[] storedHash = PasswordHasher.base64ToByte(cred.getPassword());
			byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
			try {
				byte[] inputHash = PasswordHasher.getHash(1000, oldPassword, storedSalt);
				if (Arrays.equals(inputHash, storedHash)) {
					// stare hasło się zgadza, można zmienić

					byte[] salt = PasswordHasher.createSalt();
					byte[] pass = PasswordHasher.getHash(1000, newPassword, salt);
					cred.setSalt(PasswordHasher.byteToBase64(salt));
					cred.setPassword(PasswordHasher.byteToBase64(pass));
					userCredentialDao.save(cred);

					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				return false;
			} catch (UnsupportedEncodingException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean editUser(IdoncUser user, String password) {
		UserCredential cred = userCredentialDao.get(user.getUserName());
		if (cred != null) {
			byte[] storedHash = PasswordHasher.base64ToByte(cred.getPassword());
			byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
			try {
				byte[] inputHash = PasswordHasher.getHash(1000, password, storedSalt);
				if (Arrays.equals(inputHash, storedHash)) {
					// stare hasło się zgadza, można edytować użytkownika

					user = userDao.save(user);
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				return false;
			} catch (UnsupportedEncodingException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean checkUserAuthorized(String username, String sessionId) throws IdoncException {
		LoggedUser loggedUser = loggedUserDao.getLoggedUserBySession(sessionId);
		if (loggedUser != null) {
			if (loggedUser.getUser().getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}

	// General purpose services

	public List<IdoncUser> getUserList(String username, String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.getAllUsers();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncUser getUserDetails(String queriedUsername, String username, String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.findByUsername(queriedUsername);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public List<String> getUserNameList(String username, String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.getUniqueUsernames();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public int countUsers(String username, String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.count();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public List<IdoncUser> getUsers(String username, String sessionId, int first, int count) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.get(first, count);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	@Override
	public int countUsersWithoutAdmins(String username, String sessionId) {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.countWithoutAdmins();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	@Override
	public List<IdoncUser> getUsersWithoutAdmins(String username, String sessionId, int first, int count) {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.getWithoutAdmins(first, count);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}

	}

	public IdoncUser loadUser(String username, String sessionId, Long id) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return userDao.load(id);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public List<IdoncProject> getProjects(String username, String sessionId, int first, int count) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return projectDao.get(first, count);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncProject loadProject(String username, String sessionId, Long id) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return projectDao.load(id);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public int countProjects(String username, String sessionId) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			return projectDao.count();
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	public IdoncProject getContributedProject(String username, String sessionId) throws IdoncException {
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

	// @Transactional(readOnly = false)
	public IdoncPart getPartToProcess(String username, String sessionId, boolean requiresConfirmation) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			IdoncUser user = userDao.findByUsername(username);
			IdoncProject project = user.getContributedProject();
			if (project != null) {
				// ma ustawiony projekt
				IdoncPart part;
				if (requiresConfirmation) {
					part = projectDao.getPartWithConfirmation(username, project);
				} else {
					part = projectDao.getPartWithoutConfirmation(project);
				}
				return part;
			} else {
				// nie ma ustawionego projektu
				return null;
			}
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	// @Transactional(readOnly = false)
	public void returnProcessingResult(String username, String sessionId, IdoncPart part, List<IdoncResult> result,
			boolean requiresConfirmation) throws IdoncException {
		if (checkUserAuthorized(username, sessionId)) {
			if (requiresConfirmation) {
				projectDao.returnProcessingResultWithConfirmation(username, part, result);
			} else {
				projectDao.returnProcessingResultWithoutConfirmation(part, result);
			}
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	@Override
	public boolean isAdmin(String loggedUserName, String sessionId) {
		if (checkUserAuthorized(loggedUserName, sessionId)) {
			return adminDao.isAdminUser(loggedUserName);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	@Override
	public void deleteUser(IdoncUser delUser, String loggedUserName, String sessionId) {
		if (isAdmin(loggedUserName, sessionId)) {
			loggedUserDao.deleteByIdoncUserId(delUser.getId());
			userCredentialDao.deleteByIdoncUserId(delUser.getId());
			userDao.delete(delUser.getId());
		}
	}

	@Override
	public String getInputDataForProject(Long id, String loggedUserName, String sessionId) {
		if (isAdmin(loggedUserName, sessionId)) {
			List<IdoncPart> input = projectDao.getInputData(id);
			IdoncProject proj = projectDao.load(id);

			return createXML(proj, input, false);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	@Override
	public String getOutputDataForProject(Long id, String loggedUserName, String sessionId) {
		if (isAdmin(loggedUserName, sessionId)) {
			List<IdoncPart> output = projectDao.getOutputData(id);
			IdoncProject proj = projectDao.load(id);

			return createXML(proj, output, true);
		} else {
			throw new IdoncAuthorizationException("user not authorized");
		}
	}

	private String createXML(IdoncProject proj, List<IdoncPart> parts, boolean isResult) {
		StringBuffer buf = new StringBuffer();
		if (proj != null && parts != null) {
			buf.append("<project>");
			buf.append("\n");
			buf.append("	<name>");
			buf.append(proj.getName());
			buf.append("</name>");
			buf.append("\n");
			buf.append("	<dataType>");
			buf.append(isResult == true ? "result" : "input");
			buf.append("</dataType>");
			buf.append("\n");
			for (IdoncPart part : parts) {
				buf.append("	<part>");
				buf.append("\n");
				buf.append("		<number>");
				buf.append(part.getNumber());
				buf.append("</number>");
				buf.append("\n");
				buf.append("			<data>");
				buf.append("\n");
				if (isResult) {
					//stream.println("				<value>" + part.getResults().getValue()+ "</value>");
					for (IdoncResult data : part.getResults()) {
						buf.append("				<value>");
						buf.append(data.getValue());
						buf.append("</value>");
						buf.append("\n");
					}
				} else {
					for (IdoncLongData data : part.getLongDataList()) {
						buf.append("				<value>");
						buf.append(data.getValue());
						buf.append("</value>");
						buf.append("\n");
					}
				}
				buf.append("			</data>");
				buf.append("\n");
				buf.append("	</part>");
				buf.append("\n");
			}
			buf.append("</project>");
		}
		return buf.toString();
	}

}
