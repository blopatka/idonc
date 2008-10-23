package org.lopatka.idonc.web.model.user;

import java.io.Serializable;

public class UserCredential implements Serializable {

	private static final long serialVersionUID = 8642933674178126834L;
	private IdoncUser user;
	private String password;
	private String salt;

	public UserCredential() {
		
	}
	
	public IdoncUser getUser() {
		return user;
	}

	public void setUser(IdoncUser user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
