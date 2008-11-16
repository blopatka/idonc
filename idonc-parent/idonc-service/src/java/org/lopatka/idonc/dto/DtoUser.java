package org.lopatka.idonc.dto;

import java.io.Serializable;

public class DtoUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6495675696781865688L;
	
	private Long id;
	
	private String userName;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String name;
	
	//address fields
	private String email;
	
	private String website;
	
	private String street;
	
	private String houseNumber;
	
	private String city;
	
	private String zipCode;
	
	private String country;

}
