package org.lopatka.idonc.web.model.user;

import java.io.Serializable;

public class LoggedUser implements Serializable {

	private static final long serialVersionUID = -6523051386866991998L;

	private Long id;
	
	private IdoncUser user;
	
	private String sessionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IdoncUser getUser() {
		return user;
	}

	public void setUser(IdoncUser user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
}
