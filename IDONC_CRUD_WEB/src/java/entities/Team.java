/*
 * Team.java
 *
 * Created on 21 styczeñ 2007, 21:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class Team
 * 
 * @author Bartek
 */
@Entity
@Table(name = "TEAMS")
@NamedQueries( {
        @NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :id"),
        @NamedQuery(name = "Team.findByTeamName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName")
    })
public class Team implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TEAM_NAME", nullable = false)
    private String teamName;

    @OneToMany(mappedBy = "teamId")
    private Collection<User> userCollection;
    
    /** Creates a new instance of Team */
    public Team() {
    }

    /**
     * Creates a new instance of Team with the specified values.
     * @param id the id of the Team
     */
    public Team(Integer id) {
        this.id = id;
    }

    /**
     * Creates a new instance of Team with the specified values.
     * @param id the id of the Team
     * @param teamName the teamName of the Team
     */
    public Team(Integer id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }

    /**
     * Gets the id of this Team.
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the id of this Team to the specified value.
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the teamName of this Team.
     * @return the teamName
     */
    public String getTeamName() {
        return this.teamName;
    }

    /**
     * Sets the teamName of this Team to the specified value.
     * @param teamName the new teamName
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Gets the userCollection of this Team.
     * @return the userCollection
     */
    public Collection<User> getUserCollection() {
        return this.userCollection;
    }

    /**
     * Sets the userCollection of this Team to the specified value.
     * @param userCollection the new userCollection
     */
    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Team.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Team object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team)object;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "entities.Team[id=" + id + "]";
    }
    
}
