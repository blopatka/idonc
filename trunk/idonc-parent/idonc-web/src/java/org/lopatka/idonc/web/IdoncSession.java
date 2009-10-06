package org.lopatka.idonc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.service.ConverterService;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;
import org.lopatka.idonc.service.IdoncService;

public class IdoncSession extends AuthenticatedWebSession {

    private static final long serialVersionUID = 1L;

	private LoggedUser loggedUser;
	
	private Map<String, IdoncUser> users;

	private Map<String, IdoncProject> projects;
	
	@SpringBean(name="idoncService")
	private IdoncService idoncService;
	
	@SpringBean(name="converterService")
	private ConverterService converterService;
	
	public IdoncSession(Request request) {
		super(request);
		//http://markmail.org/message/2tqrkaik3ym6nif3#query:wicket%20authenticatedwebsession%20dao+page:1+mid:trzow66cod7ebomi+state:results
		InjectorHolder.getInjector().inject(this);
		
		users = new HashMap<String, IdoncUser>();
		projects = new HashMap<String, IdoncProject>();
	}
	
	public void setIdoncService(IdoncService service) {
		this.idoncService = service;
	}
	
	public IdoncService getIdoncService() {
		return this.idoncService;
	}
	
	public void setConverterService(ConverterService service) {
		this.converterService = service;
	}
	
	public ConverterService getConverterService() {
		return this.converterService;
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

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
    }

    public String getLoggedUserName() {
        return loggedUser.getUser().getUserName();
    }

    public String getSessionId() {
        return loggedUser.getSessionId();
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
	
	public IdoncProject getProject(String name) {
		return projects.get(name);
	}

	public void setProject(String name, IdoncProject project) {
		projects.put(name, project);
	}
	
	public void setProjects(List<IdoncProject> projects) {
		for(IdoncProject project: projects) {
			this.projects.put(project.getName(), project);
		}
	}
	
	

}
