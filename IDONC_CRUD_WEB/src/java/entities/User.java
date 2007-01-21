/*
 * User.java
 *
 * Created on 21 styczeñ 2007, 21:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity class User
 * 
 * @author Bartek
 */
@Entity
@Table(name = "USERS")
@NamedQueries( {
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
        @NamedQuery(name = "User.findBySecondName", query = "SELECT u FROM User u WHERE u.secondName = :secondName"),
        @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
        @NamedQuery(name = "User.findByWebPage", query = "SELECT u FROM User u WHERE u.webPage = :webPage")
    })
public class User implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "SECOND_NAME")
    private String secondName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "WEB_PAGE")
    private String webPage;

    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    @ManyToOne
    private Address addressId;

    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Team teamId;
    
    /** Creates a new instance of User */
    public User() {
    }

    /**
     * Creates a new instance of User with the specified values.
     * @param id the id of the User
     */
    public User(Integer id) {
        this.setId(id);
    }

    /**
     * Creates a new instance of User with the specified values.
     * @param id the id of the User
     * @param username the username of the User
     * @param email the email of the User
     * @param firstName the firstName of the User
     * @param lastName the lastName of the User
     */
    public User(Integer id, String username, String email, String firstName, String lastName) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    /**
     * Gets the id of this User.
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the id of this User to the specified value.
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the username of this User.
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of this User to the specified value.
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of this User.
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of this User to the specified value.
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the firstName of this User.
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the firstName of this User to the specified value.
     * @param firstName the new firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the secondName of this User.
     * @return the secondName
     */
    public String getSecondName() {
        return this.secondName;
    }

    /**
     * Sets the secondName of this User to the specified value.
     * @param secondName the new secondName
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Gets the lastName of this User.
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the lastName of this User to the specified value.
     * @param lastName the new lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the webPage of this User.
     * @return the webPage
     */
    public String getWebPage() {
        return this.webPage;
    }

    /**
     * Sets the webPage of this User to the specified value.
     * @param webPage the new webPage
     */
    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    /**
     * Gets the addressId of this User.
     * @return the addressId
     */
    public Address getAddressId() {
        return this.addressId;
    }

    /**
     * Sets the addressId of this User to the specified value.
     * @param addressId the new addressId
     */
    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    /**
     * Gets the teamId of this User.
     * @return the teamId
     */
    public Team getTeamId() {
        return this.teamId;
    }

    /**
     * Sets the teamId of this User to the specified value.
     * @param teamId the new teamId
     */
    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this User.  The result is 
     * <code>true</code> if and only if the argument is not null and is a User object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User)object;
        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "entities.User[id=" + getId() + "]";
    }
    
}
