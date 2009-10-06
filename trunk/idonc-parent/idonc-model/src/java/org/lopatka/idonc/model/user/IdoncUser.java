package org.lopatka.idonc.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.validator.NotNull;
import org.lopatka.idonc.model.data.IdoncProject;

@Entity
@Table(name="USERS", uniqueConstraints=@UniqueConstraint(columnNames={"USER_NAME"}))
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class IdoncUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="USER_NAME")
	@NotNull
	private String userName;

	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Embedded
	private Address address;

    @ManyToOne
    @JoinColumn(name="CONTRIBUTED_PROJECT")
    @LazyToOne(LazyToOneOption.FALSE)
    private IdoncProject contributedProject;

	public IdoncUser() {
		this.address = new Address();
	}

	public IdoncUser(String userName) {
		this.userName = userName;
		this.address = new Address();
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

     public IdoncProject getContributedProject() {
        return contributedProject;
    }

    public void setContributedProject(IdoncProject contributedProject) {
        this.contributedProject = contributedProject;
    }
}
