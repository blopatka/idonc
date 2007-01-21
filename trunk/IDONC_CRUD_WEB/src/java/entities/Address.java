/*
 * Address.java
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class Address
 * 
 * @author Bartek
 */
@Entity
@Table(name = "ADDRESSES")
@NamedQueries( {
        @NamedQuery(name = "Address.findById", query = "SELECT a FROM Address a WHERE a.id = :id"),
        @NamedQuery(name = "Address.findByStreet", query = "SELECT a FROM Address a WHERE a.street = :street"),
        @NamedQuery(name = "Address.findByHouseNumber", query = "SELECT a FROM Address a WHERE a.houseNumber = :houseNumber"),
        @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city"),
        @NamedQuery(name = "Address.findByZip", query = "SELECT a FROM Address a WHERE a.zip = :zip")
    })
public class Address implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "STREET")
    private String street;

    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP")
    private Integer zip;

    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    @ManyToOne
    private Country countryId;

    @OneToMany(mappedBy = "addressId")
    private Collection<User> userCollection;
    
    /** Creates a new instance of Address */
    public Address() {
    }

    /**
     * Creates a new instance of Address with the specified values.
     * @param id the id of the Address
     */
    public Address(Integer id) {
        this.id = id;
    }

    /**
     * Gets the id of this Address.
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the id of this Address to the specified value.
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the street of this Address.
     * @return the street
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * Sets the street of this Address to the specified value.
     * @param street the new street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the houseNumber of this Address.
     * @return the houseNumber
     */
    public String getHouseNumber() {
        return this.houseNumber;
    }

    /**
     * Sets the houseNumber of this Address to the specified value.
     * @param houseNumber the new houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets the city of this Address.
     * @return the city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Sets the city of this Address to the specified value.
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the zip of this Address.
     * @return the zip
     */
    public Integer getZip() {
        return this.zip;
    }

    /**
     * Sets the zip of this Address to the specified value.
     * @param zip the new zip
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Gets the countryId of this Address.
     * @return the countryId
     */
    public Country getCountryId() {
        return this.countryId;
    }

    /**
     * Sets the countryId of this Address to the specified value.
     * @param countryId the new countryId
     */
    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the userCollection of this Address.
     * @return the userCollection
     */
    public Collection<User> getUserCollection() {
        return this.userCollection;
    }

    /**
     * Sets the userCollection of this Address to the specified value.
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
     * Determines whether another object is equal to this Address.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Address object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address)object;
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
        return "entities.Address[id=" + id + "]";
    }
    
}
