package org.lopatka.idonc.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="LOGGED_USERS")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class LoggedUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="USER")
	@LazyToOne(LazyToOneOption.FALSE)
	private IdoncUser user;

	@Column(name="SESSION_ID")
	@NotNull
	private String sessionId;

	@Column(name="LAST_UPDATED")
	private Long updated;

	@PrePersist
	@PreUpdate
	protected void onCreate() {
		updated = System.currentTimeMillis();
	}

	public Long getUpdated() {
		return updated;
	}

	public void setUpdated(Long updated) {
		this.updated = updated;
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


}
