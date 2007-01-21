package entities;
/*
 * Country.java
 *
 * Created on 21 styczeñ 2007, 21:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

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
 * Entity class Country
 * 
 * @author Bartek
 */
@Entity
@Table(name = "COUNTRIES")
@NamedQueries( {
        @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c WHERE c.id = :id"),
        @NamedQuery(name = "Country.findByCountryName", query = "SELECT c FROM Country c WHERE c.countryName = :countryName")
    })
public class Country implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "COUNTRY_NAME", nullable = false)
    private String countryName;

    @OneToMany(mappedBy = "countryId")
    private Collection<Address> addressCollection;
    
    /** Creates a new instance of Country */
    public Country() {
    }

    /**
     * Creates a new instance of Country with the specified values.
     * @param id the id of the Country
     */
    public Country(Integer id) {
        this.id = id;
    }

    /**
     * Creates a new instance of Country with the specified values.
     * @param id the id of the Country
     * @param countryName the countryName of the Country
     */
    public Country(Integer id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    /**
     * Gets the id of this Country.
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the id of this Country to the specified value.
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the countryName of this Country.
     * @return the countryName
     */
    public String getCountryName() {
        return this.countryName;
    }

    /**
     * Sets the countryName of this Country to the specified value.
     * @param countryName the new countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the addressCollection of this Country.
     * @return the addressCollection
     */
    public Collection<Address> getAddressCollection() {
        return this.addressCollection;
    }

    /**
     * Sets the addressCollection of this Country to the specified value.
     * @param addressCollection the new addressCollection
     */
    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
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
     * Determines whether another object is equal to this Country.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Country object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country)object;
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
        return "entities.Country[id=" + id + "]";
    }
    
}
