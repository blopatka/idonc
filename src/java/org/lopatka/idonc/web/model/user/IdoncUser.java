package org.lopatka.idonc.web.model.user;

import java.io.Serializable;

public class IdoncUser implements Serializable {

	private static final long serialVersionUID = 664519288014634509L;

	private Long id;

	private String userName;
	
	private String password;

	private String firstName;
	
	private String lastName;
	
	private Address address;

	public IdoncUser() {
		this.address = new Address();
	}

	public IdoncUser(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
	
}
