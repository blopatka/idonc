package org.lopatka.idonc.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="USER_CREDENTIALS")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserCredential implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="USER")
	@LazyToOne(LazyToOneOption.FALSE)
	private IdoncUser user;

	@Column(name="PASSWORD")
	@NotNull
	private String password;

	@Column(name="SALT")
	@NotNull
	private String salt;

	public UserCredential() {

	}

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
