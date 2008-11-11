package org.lopatka.idonc.web.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Embeddable
public class Address implements Serializable{

	private static final long serialVersionUID = -8283647876262623680L;

	@Column(name="EMAIL")
	@NotNull
	private String email;

	@Column(name="WEBSITE")
	private String website;

	@Column(name="STREET")
	private String street;

	@Column(name="HOUSE_NUMBER")
	private String houseNumber;

	@Column(name="CITY")
	private String city;

	@Column(name="ZIP_CODE")
	private String zipCode;

	@Column(name="COUNTRY")
	private String country;

	public Address() {
		
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}
