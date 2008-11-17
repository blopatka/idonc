package org.lopatka.idonc.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.lopatka.idonc.dao.LoggedUserDao;
import org.lopatka.idonc.dao.ProjectDao;
import org.lopatka.idonc.dao.UserCredentialDao;
import org.lopatka.idonc.dao.UserDao;
import org.lopatka.idonc.exception.IdoncAuthorizationException;
import org.lopatka.idonc.exception.IdoncException;
import org.lopatka.idonc.model.data.IdoncProject;
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

    //Services for login and registering
    public LoggedUser loginUser(String username, String password) {
        if ((username != null) && (password != null)) {
            UserCredential cred = userCredentialDao.get(username);
            byte[] storedHash = PasswordHasher.base64ToByte(cred.getPassword());
            byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
            try {
                byte[] inputHash = PasswordHasher.getHash(1000, password, storedSalt);
                if (Arrays.equals(inputHash, storedHash)) {
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

    public boolean checkUserAuthorized(String username, String sessionId) throws IdoncException {
        LoggedUser loggedUser = loggedUserDao.getLoggedUserBySession(sessionId);
        if (loggedUser != null) {
            if(loggedUser.getUser().getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //General purpose services
   
    public List<IdoncUser> getUserList(String username, String sessionId) throws IdoncException{
        if (checkUserAuthorized(username, sessionId)) {
            // TODO Auto-generated method stub
            return null;
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }

    public void updateUser(String username, String sessionId, IdoncUser user) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            // TODO Auto-generated method stub
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }

    public IdoncUser getUserDetails(String queriedUsername, String username, String sessionId) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            // TODO Auto-generated method stub
            return null;
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }

    public List<String> getUserNameList(String username, String sessionId) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            // TODO Auto-generated method stub
            return null;
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

    public List getUsers(String username, String sessionId, int first, int count) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            return userDao.get(first, count);
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }

    public IdoncUser loadUser(String username, String sessionId, long id) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            return userDao.load(id);
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }

    public List getProjects(String username, String sessionId, int first, int count) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            return projectDao.get(first, count);
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }

    public IdoncProject loadProject(String username, String sessionId, long id) throws IdoncException {
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

    /*
    public void addProject(String username, String sessionId, IdoncProject project) throws IdoncException {
        if (checkUserAuthorized(username, sessionId)) {
            projectDao.add(project);
        } else {
            throw new IdoncAuthorizationException("user not authorized");
        }
    }
     */
}
