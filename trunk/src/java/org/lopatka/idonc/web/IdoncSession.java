package org.lopatka.idonc.web;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.dao.UserCredentialDao;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.model.user.LoggedUser;
import org.lopatka.idonc.web.model.user.UserCredential;
import org.lopatka.idonc.web.service.IdoncService;
import org.lopatka.idonc.web.utils.PasswordHasher;

public class IdoncSession extends AuthenticatedWebSession {

	private LoggedUser loggedUser;
	
	private Map<String, IdoncUser> users;
	
	@SpringBean(name="idoncService")
	private IdoncService idoncService;
	
	private static final long serialVersionUID = -3623974092048074997L;
	
	public IdoncSession(Request request) {
		super(request);
		//http://markmail.org/message/2tqrkaik3ym6nif3#query:wicket%20authenticatedwebsession%20dao+page:1+mid:trzow66cod7ebomi+state:results
		InjectorHolder.getInjector().inject(this);
		
		users = new HashMap<String, IdoncUser>();
	}
	
	public void setIdoncService(IdoncService service) {
		this.idoncService = service;
	}
	
	public static IdoncSession get() {
		return (IdoncSession) Session.get();
	}

	@Override
	public boolean authenticate(String username, String password) {
		//return username.equals(password);
		LoggedUser logU = idoncService.loginUser(username, password);
		if (logU == null) {
			loggedUser = null;
			return false;
		} else {
			loggedUser = logU;
			return true;
		}
	}

	@Override
	public Roles getRoles() {
		if(isSignedIn()) {
			return new Roles(Roles.ADMIN);
		}
		return null;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUserName(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public IdoncUser getUser(String username) {
		return users.get(username);
	}

	public void setUser(String username, IdoncUser user) {
		users.put(username, user);
	}
	
	public void setUsers(List<IdoncUser> users) {
		for (IdoncUser user : users) {
			this.users.put(user.getUserName(), user);
		}
	}

	@Override
	public void invalidate() {
		idoncService.logoutUser(loggedUser.getUser().getUserName(), loggedUser.getSessionId());
		super.invalidate();
	}
	
	

}
